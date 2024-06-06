package com.example.Instructorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Instructorservice.model.Token;
import com.example.Instructorservice.model.User;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
    Optional<Token> findByUser(User user);
}
