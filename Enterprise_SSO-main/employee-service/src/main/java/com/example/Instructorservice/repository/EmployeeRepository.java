package com.example.Instructorservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Instructorservice.model.Employee;
import com.example.Instructorservice.model.User;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByUser(User user);
}
