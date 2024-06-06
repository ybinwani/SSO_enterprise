package com.example.salaryservice.repository;

import com.example.salaryservice.model.Salary;
import com.example.salaryservice.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
    Optional<Salary> findByUser(User user);
}
