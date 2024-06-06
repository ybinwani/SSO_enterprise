package com.example.employeeservice.service;

import com.example.employeeservice.model.Employee;
import com.example.employeeservice.model.User;
import com.example.employeeservice.repository.EmployeeRepository;

import com.example.employeeservice.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class EmployeeService {

	@Autowired
    private EmployeeRepository employeeRepository;

    public Optional<Employee> getEmployeeDetailsByUser(User user) {
        return employeeRepository.findByUser(user);
    }

    public Optional<Employee> registerEmployee(EmployeeDTO employeeDTO, User user) {
        if (employeeRepository.findByUser(user).isPresent()) {
            throw new IllegalArgumentException("Employee already registered");
        }
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setId(employeeDTO.getId());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setExperience(employeeDTO.getExperience());
//        if (file != null && !file.isEmpty()) {
//            employee.setFileData(file.getBytes());
//        }
        employee = employeeRepository.save(employee);
        return Optional.of(employee);
    }
}
