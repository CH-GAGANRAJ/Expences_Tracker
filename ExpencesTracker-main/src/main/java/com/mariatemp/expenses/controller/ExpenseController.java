package com.mariatemp.expenses.controller;

import com.mariatemp.expenses.dto.ExpenseDto;
import com.mariatemp.expenses.mapper.ExpenseMapper;
import com.mariatemp.expenses.model.Expense;
import com.mariatemp.expenses.model.User;
import com.mariatemp.expenses.service.IExpenseService;
import com.mariatemp.expenses.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {
    private final IExpenseService expenseService;

    @Autowired
    public ExpenseController(IExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<Page<ExpenseDto>> getFilteredExpenses(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size,
                                                                @RequestParam(required = false) String category,
                                                                @RequestParam(required = false)LocalDate startDate,
                                                                @RequestParam(required = false) LocalDate endDate) {

        Page<ExpenseDto> expenses = expenseService.getFilteredExpenses(page, size, category, startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseDtoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(@Valid @RequestBody ExpenseDto expenseDto) {
        return ResponseEntity.ok(expenseService.createExpense(expenseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseDto updatedExpenseDto) {
        return expenseService.updateExpense(id, updatedExpenseDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }
}
