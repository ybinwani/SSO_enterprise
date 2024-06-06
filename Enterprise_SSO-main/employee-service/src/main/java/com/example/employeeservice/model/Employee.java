package com.example.employeeservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.employeeservice.model.User;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
//    @Lob
//    @Column(name = "file_data", columnDefinition = "LONGBLOB")
//    private byte[] fileData;
    
    private String experience;

    private String department;
}
