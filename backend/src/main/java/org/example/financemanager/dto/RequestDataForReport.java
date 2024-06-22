package org.example.financemanager.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestDataForReport {
    private LocalDate startDate;
    private LocalDate endDate;
    private String type;
    private String currency;
}
