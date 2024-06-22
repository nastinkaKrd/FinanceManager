package org.example.financemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AmountWithCurrencyResponse {
    private Double amount;
    private String currency;
}
