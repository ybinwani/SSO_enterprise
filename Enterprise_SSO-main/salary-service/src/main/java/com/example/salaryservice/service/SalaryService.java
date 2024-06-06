package com.example.salaryservice.service;

import com.example.salaryservice.model.Salary;
import com.example.salaryservice.model.User;
import com.example.salaryservice.repository.SalaryRepository;
import com.example.salaryservice.dto.SalaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SalaryService {

	@Autowired
    private SalaryRepository salaryRepository;

    public Optional<Salary> getSalaryDetailsByUser(User user) {
        return salaryRepository.findByUser(user);
    }

    public Optional<Salary> registerSalary(SalaryDTO salaryDTO, User user) {
        if (salaryRepository.findByUser(user).isPresent()) {
            throw new IllegalArgumentException("Salary already registered");
        }
        Salary salary = new Salary();
        salary.setUser(user);
        salary.setAmount(salaryDTO.getAmount());
        salary.setCurrency(salaryDTO.getCurrency());
        salary = salaryRepository.save(salary);
        return Optional.of(salary);
    }
}
