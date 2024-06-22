package org.example.financemanager.service;

import lombok.RequiredArgsConstructor;
import org.example.financemanager.domain.Category;
import org.example.financemanager.dto.CategoryDto;
import org.example.financemanager.exception.ApiExceptionAlreadyReported;
import org.example.financemanager.exception.ApiExceptionNotFound;
import org.example.financemanager.mapper.CategoryMapper;
import org.example.financemanager.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImplements implements CategoryService{
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    @Override
    public List<CategoryDto> getCategories(String username) {
        List<CategoryDto> categoryDtos = categoryMapper.toDtos(categoryRepository.findAllByUser_UsernameOrderById(username));
        categoryRepository.findAllWithNullUserId().forEach(
                category -> categoryDtos.add(categoryMapper.toDto(category))
        );
        return categoryDtos;
    }

    @Override
    public void addCategory(CategoryDto categoryDto, String username) {
        categoryRepository.findByTitle(categoryDto.getTitle()).ifPresentOrElse(
                tempCategory -> {
                    if (tempCategory.getUser().getUsername().equals(username)){
                        throw new ApiExceptionAlreadyReported("This category already exists");
                    }else {
                        Category category = categoryMapper.toDomain(categoryDto);
                        category.setUser(userService.getUserByUsername(username));
                        categoryRepository.save(category);
                    }
                },
                () -> {
                    Category category = categoryMapper.toDomain(categoryDto);
                    category.setUser(userService.getUserByUsername(username));
                    categoryRepository.save(category);
                }
        );
    }

    @Override
    public void changeCategory(Integer id, CategoryDto categoryDto, String username) {
        categoryRepository.findById(id).ifPresentOrElse(category -> {
            if (category.getUser() == null || !category.getUser().getUsername().equals(username)){
                throw new RuntimeException("You are forbidden to do this");
            }else {
                category.setTitle(categoryDto.getTitle());
                category.setDescription(categoryDto.getDescription());
                categoryRepository.save(category);
            }
        }, () -> {
            throw new ApiExceptionNotFound("The category is not found");
        });
    }

    @Override
    public void deleteCategory(Integer id, String username) {
        categoryRepository.findById(id).ifPresentOrElse(category -> {
            if (category.getUser() == null || !category.getUser().getUsername().equals(username)){
                throw new RuntimeException("You are forbidden to do this");
            }else {
                categoryRepository.delete(category);
            }
        }, () -> {
            throw new ApiExceptionNotFound("The category is not found");
        });
    }
}
