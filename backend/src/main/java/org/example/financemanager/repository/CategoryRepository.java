package org.example.financemanager.repository;

import org.example.financemanager.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByTitle(String title);

    List<Category> findAllByUser_UsernameOrderById(String username);

    @Query("SELECT c FROM Category c WHERE c.user IS NULL")
    List<Category> findAllWithNullUserId();

}
