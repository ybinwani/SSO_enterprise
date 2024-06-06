package com.example.employeeservice.dto;

import org.springframework.web.multipart.MultipartFile;

import com.example.employeeservice.model.Employee;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmployeeDTO {
    private Long id;
    private String username;
    private String department;
    private String experience;
    private String message;
//    private byte[] fileData;
    
}
