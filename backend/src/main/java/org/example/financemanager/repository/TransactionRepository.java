package org.example.financemanager.repository;

import org.example.financemanager.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findAllByUser_UsernameOrderByDateDesc(String username);

    List<Transaction> findAllByCategory_Id(Integer id);

    Optional<Transaction> findByDescriptionAndUser_Username(String description, String username);
}
