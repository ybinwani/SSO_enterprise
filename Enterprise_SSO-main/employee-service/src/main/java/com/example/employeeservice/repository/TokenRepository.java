package com.example.employeeservice.repository;

import com.example.employeeservice.model.Token;
import com.example.employeeservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUser(User user);
}
