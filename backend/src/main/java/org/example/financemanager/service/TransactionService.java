package org.example.financemanager.service;

import org.example.financemanager.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getTransactions(String username);

    void addTransaction(TransactionDto transactionDto, String username);

    void changeTransaction(Integer id, TransactionDto transactionDto, String username);

    void deleteTransaction(Integer id, String username);

    List<TransactionDto> filterByCategory(Integer categoryId, String username);

    TransactionDto getByDescription(String description, String username);
}
