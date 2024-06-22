package org.example.financemanager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDto {
    private Integer id;
    private String description;
    private LocalDate date;
    private String type;
    private Double amount;
    private String currency;
    private CategoryDto category;
    private UserDto user;
}
