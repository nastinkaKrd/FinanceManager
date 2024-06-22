package org.example.financemanager.mapper;

import org.example.financemanager.domain.Transaction;
import org.example.financemanager.dto.TransactionDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends BaseMapper<Transaction, TransactionDto> {
}
