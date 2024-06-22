package org.example.financemanager.repository;

import org.example.financemanager.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE User e SET e.isEmailVerified = true WHERE e.email = :userEmail")
    void updateUserEmailVerifyFieldByUserEmail(@Param("userEmail") String userEmail);

}
