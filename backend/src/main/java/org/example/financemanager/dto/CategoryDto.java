package org.example.financemanager.dto;

import lombok.Data;

@Data
public class CategoryDto {
    private Integer id;
    private String title;
    private String description;
    private UserDto user;
}
