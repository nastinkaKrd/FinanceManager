package org.example.financemanager.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
@Builder
public class GeneralReportResponse {
    private Map<LocalDate, AmountWithCurrencyResponse> data;
}
