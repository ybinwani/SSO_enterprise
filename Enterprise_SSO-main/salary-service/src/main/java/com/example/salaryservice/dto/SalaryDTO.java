package com.example.salaryservice.dto;

import com.example.salaryservice.model.Salary;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SalaryDTO {
    private Long id;
    private String username;
    private double amount;
    private String currency;
    private String message;
}
