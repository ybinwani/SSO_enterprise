package com.example.Instructorservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Instructorservice.dto.EmployeeDTO;
import com.example.Instructorservice.model.Employee;
import com.example.Instructorservice.model.User;
import com.example.Instructorservice.repository.EmployeeRepository;

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
