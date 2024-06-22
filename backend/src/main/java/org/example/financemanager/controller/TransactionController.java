package org.example.financemanager.controller;

import lombok.AllArgsConstructor;
import org.example.financemanager.domain.Currency;
import org.example.financemanager.domain.TransactionTypes;
import org.example.financemanager.dto.TransactionDto;
import org.example.financemanager.service.TransactionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping
    public List<TransactionDto> getTransactions(@AuthenticationPrincipal UserDetails userDetails){
        return transactionService.getTransactions(userDetails.getUsername());
    }

    @PostMapping
    public void addTransaction(@RequestBody TransactionDto transactionDto, @AuthenticationPrincipal UserDetails userDetails){
        transactionService.addTransaction(transactionDto, userDetails.getUsername());
    }

    @PutMapping("/{id}")
    public void changeTransaction(@PathVariable(name = "id") Integer id,
                                  @RequestBody TransactionDto transactionDto,
                                  @AuthenticationPrincipal UserDetails userDetails){
         transactionService.changeTransaction(id, transactionDto, userDetails.getUsername());
    }


    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable(name = "id") Integer id, @AuthenticationPrincipal UserDetails userDetails){
        transactionService.deleteTransaction(id, userDetails.getUsername());
    }

    @GetMapping("/types")
    public String[] getTransactionTypes(){
        return Arrays.stream(TransactionTypes.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/currency")
    public String[] getCurrency(){
        return Arrays.stream(Currency.values())
                .map(Enum::name)
                .toArray(String[]::new);
    }

    @GetMapping("/filter")
    public List<TransactionDto> filterByCategory(@RequestParam(name = "category-id") Integer categoryId, @AuthenticationPrincipal UserDetails userDetails){
        return transactionService.filterByCategory(categoryId, userDetails.getUsername());
    }

    @GetMapping("/{description}")
    public TransactionDto getByDescription(@PathVariable(name = "description") String description, @AuthenticationPrincipal UserDetails userDetails){
        return transactionService.getByDescription(description, userDetails.getUsername());
    }
}
