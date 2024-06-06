package com.example.Instructorservice.model;

import com.example.Instructorservice.model.User;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
