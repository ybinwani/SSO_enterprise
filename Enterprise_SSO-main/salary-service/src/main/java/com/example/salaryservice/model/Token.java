package com.example.salaryservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    public Token(String token) {
        this.token = token;
        this.isLoggedOutEmployee = false;
        this.isLoggedOutSalary = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    private boolean isLoggedOutEmployee;
    private boolean isLoggedOutSalary;
}