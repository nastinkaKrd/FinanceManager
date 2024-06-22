package org.example.financemanager.service;

import org.example.financemanager.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getCategories(String username);

    void addCategory(CategoryDto categoryDto, String username);

    void changeCategory(Integer id, CategoryDto categoryDto, String username);

    void deleteCategory(Integer id, String username);
}
