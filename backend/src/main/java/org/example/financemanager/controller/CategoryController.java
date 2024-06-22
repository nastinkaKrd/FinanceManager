package org.example.financemanager.controller;

import lombok.AllArgsConstructor;
import org.example.financemanager.dto.CategoryDto;
import org.example.financemanager.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@AuthenticationPrincipal UserDetails userDetails){
        return categoryService.getCategories(userDetails.getUsername());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCategory(@RequestBody CategoryDto categoryDto, @AuthenticationPrincipal UserDetails userDetails){
        categoryService.addCategory(categoryDto, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void changeCategory(@PathVariable(name = "id") Integer id, @RequestBody CategoryDto categoryDto, @AuthenticationPrincipal UserDetails userDetails){
        categoryService.changeCategory(id, categoryDto, userDetails.getUsername());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetails userDetails){
        categoryService.deleteCategory(id, userDetails.getUsername());
    }
}
