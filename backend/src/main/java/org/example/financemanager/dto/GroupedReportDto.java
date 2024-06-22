package org.example.financemanager.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class GroupedReportDto {
    private AmountWithCurrencyResponse amount;
    private Map<String, AmountWithCurrencyResponse> dataWithTotalAmountOfCategory;
    private Map<String, Double> dataWithPercentage;
}
