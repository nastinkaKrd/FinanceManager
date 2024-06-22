package org.example.financemanager.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.example.financemanager.dto.AmountWithCurrencyResponse;
import org.example.financemanager.dto.GroupedReportDto;
import org.example.financemanager.dto.RequestDataForReport;
import org.example.financemanager.dto.GeneralReportResponse;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class ReportServiceImplements implements ReportService{
    private final TransactionService transactionService;

    @Override
    public GroupedReportDto generateGroupedReport(RequestDataForReport requestDataForReport, String username) {
        AtomicReference<Double> totalAmount = new AtomicReference<>((double) 0);
        Map<String, AmountWithCurrencyResponse> dataWithTotalAmountOfCategory = new HashMap<>();
        AtomicReference<LocalDate> currentDate = new AtomicReference<>(requestDataForReport.getStartDate());
        AtomicReference<Double> amount = new AtomicReference<>((double) 0);
        transactionService.getTransactions(username).forEach(
                transactionDto -> {
                    while (!currentDate.get().isAfter(requestDataForReport.getEndDate())){
                        if (transactionDto.getUser().getUsername().equals(username)
                                && transactionDto.getType().equals(requestDataForReport.getType())
                                && transactionDto.getDate().equals(currentDate.get())){
                            if (requestDataForReport.getCurrency().equals(transactionDto.getCurrency())){
                                amount.set(transactionDto.getAmount());
                            }else {
                                try {
                                    amount.set(getExchangeRateData(transactionDto.getCurrency(), requestDataForReport.getCurrency(), transactionDto.getAmount()));

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            totalAmount.updateAndGet(a -> a + amount.get());
                            if (dataWithTotalAmountOfCategory.containsKey(transactionDto.getCategory().getTitle())){
                                dataWithTotalAmountOfCategory.put(transactionDto.getCategory().getTitle(),
                                        new AmountWithCurrencyResponse(dataWithTotalAmountOfCategory.get(transactionDto.getCategory().getTitle())
                                                .getAmount()+amount.get(), requestDataForReport.getCurrency()));
                            }else {
                                dataWithTotalAmountOfCategory.put(transactionDto.getCategory().getTitle(), new AmountWithCurrencyResponse(amount.get(), requestDataForReport.getCurrency()));
                            }

                        }
                        currentDate.updateAndGet( date -> date.plusDays(1));
                    }
                    currentDate.updateAndGet( date -> requestDataForReport.getStartDate());
                }
        );
        Map<String, Double> report = new HashMap<>();
        dataWithTotalAmountOfCategory.forEach((c, a) -> report.put(c, (a.getAmount()/totalAmount.get())*100));
        return GroupedReportDto.builder()
                .amount(new AmountWithCurrencyResponse(totalAmount.get(), requestDataForReport.getCurrency()))
                .dataWithTotalAmountOfCategory(dataWithTotalAmountOfCategory)
                .dataWithPercentage(report).build();
    }

    @Override
    public GeneralReportResponse generateTotalReport(RequestDataForReport requestDataForReport, String username) {
        Map<LocalDate, AmountWithCurrencyResponse> report = new TreeMap<>();
        AtomicReference<LocalDate> currentDate = new AtomicReference<>(requestDataForReport.getStartDate());
        AtomicReference<Double> amount = new AtomicReference<>((double) 0);
        transactionService.getTransactions(username).forEach(
                transactionDto -> {
                    while (!currentDate.get().isAfter(requestDataForReport.getEndDate())){
                        if (transactionDto.getUser().getUsername().equals(username)
                                && transactionDto.getType().equals(requestDataForReport.getType())
                                && transactionDto.getDate().equals(currentDate.get())){
                            if (requestDataForReport.getCurrency().equals(transactionDto.getCurrency())){
                                amount.set(transactionDto.getAmount());
                            }else {
                                try {
                                    amount.set(getExchangeRateData(transactionDto.getCurrency(), requestDataForReport.getCurrency(), transactionDto.getAmount()));
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            if (report.containsKey(currentDate.get())){
                                report.put(currentDate.get(),
                                        new AmountWithCurrencyResponse(report.get(currentDate.get()).getAmount() + amount.get(),
                                                requestDataForReport.getCurrency()));
                            }else {
                                report.put(currentDate.get(), new AmountWithCurrencyResponse(amount.get(), requestDataForReport.getCurrency()));
                            }
                        }
                        currentDate.updateAndGet( date -> date.plusDays(1));
                    }
                    currentDate.updateAndGet( date -> requestDataForReport.getStartDate());
                }
        );
        return GeneralReportResponse.builder()
                .data(report).build();
    }


    private Double getExchangeRateData(String requestCurrency, String responseCurrency, Double amount) throws IOException {
        double data;
        StringBuilder responseData = new StringBuilder();
        JsonObject jsonObject;
        URL url;
        url = new URL("https://api.exchangerate-api.com/v4/latest/" + requestCurrency);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                responseData.append(line);
            }
            jsonObject = new Gson().fromJson(responseData.toString(), JsonObject.class);
            JsonObject rates = jsonObject.getAsJsonObject("rates");
            double exchangeRate = rates.get(responseCurrency).getAsDouble();
            data = amount * exchangeRate;
        }

        return data;
    }
}
