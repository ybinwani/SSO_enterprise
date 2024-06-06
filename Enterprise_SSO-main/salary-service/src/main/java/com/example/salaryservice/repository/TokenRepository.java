package com.example.salaryservice.repository;

import com.example.salaryservice.model.Token;
import com.example.salaryservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUser(User user);
}
