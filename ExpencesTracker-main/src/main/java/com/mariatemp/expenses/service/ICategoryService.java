package com.mariatemp.expenses.service;

import com.mariatemp.expenses.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {

    Category saveCategory(Category category);

    List<Category> getAllCategories();
    Optional<Category> getCategoryById(Long id);
    Optional<Category> getCategoryByName(String name);
    void deleteCategory(Long id);
}
