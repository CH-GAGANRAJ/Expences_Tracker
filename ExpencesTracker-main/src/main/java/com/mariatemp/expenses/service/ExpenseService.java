package com.mariatemp.expenses.service;

import com.mariatemp.expenses.dto.ExpenseDto;
import com.mariatemp.expenses.mapper.ExpenseMapper;
import com.mariatemp.expenses.model.Category;
import com.mariatemp.expenses.model.Expense;
import com.mariatemp.expenses.model.User;
import com.mariatemp.expenses.repository.CategoryRepository;
import com.mariatemp.expenses.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service implementation for managing expenses.
 * Uses ExpenseRepository to interact with the database.
 */
@Service
public class ExpenseService implements IExpenseService {

    private final ExpenseRepository expenseRepository;
    private final IUserService userService;
    private final CategoryRepository categoryRepository;

    /**
     * Constructor injection of the ExpenseRepository.
     * Spring automatically provides the repository instance.
     */
    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, IUserService userService, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Saves or updates an expense in the database.
     * If the expense has no id, a new record will be created.
     * If the expense has an id, the existing record will be updated.
     *
     * @param expense   the expense object to be saved
     * @return          the saved expense with updated id (if newly created).
     */
    @Override
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    /**
     * Retrieves all expenses from the database.
     *
     * @return  a list of all expenses.
     */
    @Override
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * Finds an expense by its id.
     *
     * @param id    the id of the expense.
     * @return      an Optional containing the expense if found, or empty otherwise.
     */
    @Override
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }

    /**
     * Deletes an expense by its id.
     *
     * @param id    the id of the expense to be deleted.
     */
    @Override
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    @Override
    public List<ExpenseDto> getAllExpensesDto() {
        return getAllExpenses().stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ExpenseDto> getExpenseDtoById(Long id) {
        return getExpenseById(id).map(ExpenseMapper::toDto);
    }

    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDto) {
        User user = userService.getUserById(expenseDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Expense expense = ExpenseMapper.toEntity(expenseDto, user, category);
        Expense savedExpense = saveExpense(expense);
        return ExpenseMapper.toDto(savedExpense);
    }

    @Override
    public Optional<ExpenseDto> updateExpense(Long id, ExpenseDto updatedExpenseDto) {
        return getExpenseById(id).map(existingExpense -> {
            existingExpense.setDescription(updatedExpenseDto.getDescription());
            existingExpense.setAmount(updatedExpenseDto.getAmount());
            existingExpense.setDate(updatedExpenseDto.getDate());

            if (updatedExpenseDto.getUserId() != null) {
                User user = userService.getUserById(updatedExpenseDto.getUserId())
                        .orElseThrow(() -> new RuntimeException("User not found"));
                existingExpense.setUser(user);
            }

            if (updatedExpenseDto.getCategoryId() != null) {
                Category category = categoryRepository.findById(updatedExpenseDto.getCategoryId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                existingExpense.setCategory(category);
            }

            Expense savedExpense = saveExpense(existingExpense);
            return ExpenseMapper.toDto(savedExpense);
        });
    }

    public Page<ExpenseDto> getFilteredExpenses(int page, int size, String category, LocalDate startDate, LocalDate endDate) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Expense> expensesPage = expenseRepository.findAll(pageable);

        List<Expense> filtered = expensesPage.getContent().stream()
                .filter(e -> category == null || e.getCategory().getName().equalsIgnoreCase(category))
                .filter(e -> startDate == null || !e.getDate().isBefore(startDate))
                .filter(e -> endDate == null || !e.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        List<ExpenseDto> dtos = filtered.stream()
                .map(ExpenseMapper::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, expensesPage.getTotalElements());
    }
}
