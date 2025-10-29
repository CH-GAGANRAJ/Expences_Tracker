package com.mariatemp.expenses.service;

import com.mariatemp.expenses.dto.ExpenseDto;
import com.mariatemp.expenses.model.Expense;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing expenses.
 * Defines the basic CRUD operations.
 */
public interface IExpenseService {

    Expense saveExpense(Expense expense);
    List<Expense> getAllExpenses();
    Optional<Expense> getExpenseById(Long id);
    void deleteExpense(Long id);
    List<ExpenseDto> getAllExpensesDto();
    Optional<ExpenseDto> getExpenseDtoById(Long id);
    ExpenseDto createExpense(ExpenseDto expenseDto);
    Optional<ExpenseDto> updateExpense(Long id, ExpenseDto updatedExpenseDto);
    Page<ExpenseDto> getFilteredExpenses(int page, int size, String category, LocalDate startDate, LocalDate endDate);
}
