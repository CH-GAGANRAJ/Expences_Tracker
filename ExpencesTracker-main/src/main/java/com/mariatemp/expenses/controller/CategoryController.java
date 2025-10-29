package com.mariatemp.expenses.controller;

import com.mariatemp.expenses.dto.CategoryDto;
import com.mariatemp.expenses.mapper.CategoryMapper;
import com.mariatemp.expenses.model.Category;
import com.mariatemp.expenses.service.CategoryService;
import com.mariatemp.expenses.service.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.getAllCategories()
                .stream()
                .map(CategoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id)
                .map(CategoryMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto dto) {
        Category category = CategoryMapper.toEntity(dto);
        Category saved = categoryService.saveCategory(category);
        return ResponseEntity.ok(CategoryMapper.toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDto dto) {
        return categoryService.getCategoryById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    Category updated = categoryService.saveCategory(existing);
                    return ResponseEntity.ok(CategoryMapper.toDto(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
