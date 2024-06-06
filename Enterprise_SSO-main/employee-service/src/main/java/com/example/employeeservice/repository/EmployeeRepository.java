package com.example.employeeservice.repository;

import com.example.employeeservice.model.Employee;
import com.example.employeeservice.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);
}
