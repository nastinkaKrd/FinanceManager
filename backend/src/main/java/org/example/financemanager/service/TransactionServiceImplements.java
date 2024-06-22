package org.example.financemanager.service;

import lombok.RequiredArgsConstructor;
import org.example.financemanager.domain.Category;
import org.example.financemanager.domain.Transaction;
import org.example.financemanager.domain.User;
import org.example.financemanager.dto.TransactionDto;
import org.example.financemanager.exception.ApiExceptionNotFound;
import org.example.financemanager.mapper.CategoryMapper;
import org.example.financemanager.mapper.TransactionMapper;
import org.example.financemanager.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImplements implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    @Override
    public List<TransactionDto> getTransactions(String username) {
        return transactionMapper.toDtos(transactionRepository.findAllByUser_UsernameOrderByDateDesc(username));
    }

    @Override
    public void addTransaction(TransactionDto transactionDto, String username) {
        Transaction transaction = transactionMapper.toDomain(transactionDto);
        transaction.setUser(userService.getUserByUsername(username));
        transactionRepository.save(transaction);
    }

    @Override
    public void changeTransaction(Integer id, TransactionDto transactionDto, String username) {
        transactionRepository.findById(id).ifPresentOrElse(
                tempTransaction -> {
                    if (tempTransaction.getUser().getUsername().equals(username)){
                        Transaction transaction = transactionMapper.toDomain(transactionDto);
                        User user = userService.getUserByUsername(username);
                        Category category = categoryMapper.toDomain(transactionDto.getCategory());
                        category.setUser(user);
                        transaction.setId(id);
                        transaction.setCategory(category);
                        transaction.setUser(user);
                        transactionRepository.save(transaction);
                    }
                },
                () -> {
                    throw new ApiExceptionNotFound("The transaction is not found");
                }
        );
    }

    @Override
    public void deleteTransaction(Integer id, String username) {
        transactionRepository.findById(id).ifPresentOrElse(
                transaction -> {
                    if (transaction.getUser().getUsername().equals(username)){
                        transactionRepository.delete(transaction);
                    }
                },
                () -> {
                    throw new ApiExceptionNotFound("The transaction is not found");
                }
        );
    }

    @Override
    public List<TransactionDto> filterByCategory(Integer categoryId, String username) {
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactionRepository.findAllByCategory_Id(categoryId).forEach(transaction -> {
            if (transaction.getUser().getUsername().equals(username)){
                transactionDtos.add(transactionMapper.toDto(transaction));
            }
        });
        return transactionDtos;
    }

    @Override
    public TransactionDto getByDescription(String description, String username) {
        return transactionMapper.toDto(transactionRepository.findByDescriptionAndUser_Username(description, username).orElseThrow(
                () -> new ApiExceptionNotFound("Transaction is not found")
        ));
    }
}
