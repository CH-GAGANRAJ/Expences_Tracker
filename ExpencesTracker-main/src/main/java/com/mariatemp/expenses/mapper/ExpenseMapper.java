package com.mariatemp.expenses.mapper;

import com.mariatemp.expenses.dto.ExpenseDto;
import com.mariatemp.expenses.model.Category;
import com.mariatemp.expenses.model.Expense;
import com.mariatemp.expenses.model.User;

public class ExpenseMapper {

    public static ExpenseDto toDto(Expense expense) {
        if (expense == null) {
            return null;
        }

        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setAmount(expense.getAmount());
        dto.setDescription(expense.getDescription());
        dto.setDate(expense.getDate());

        if (expense.getUser() != null) {
            dto.setUserId(expense.getUser().getId());
        }

        if (expense.getCategory() != null) {
            dto.setCategoryId(expense.getCategory().getId());
            dto.setCategoryName(expense.getCategory().getName());
        }

        return dto;
    }

    public static Expense toEntity(ExpenseDto dto, User user, Category category) {
        if (dto == null) {
            return null;
        }

        Expense expense = new Expense();
        expense.setAmount(dto.getAmount());
        expense.setDescription(dto.getDescription());
        expense.setDate(dto.getDate());
        expense.setUser(user);
        expense.setCategory(category);

        return expense;
    }
}
