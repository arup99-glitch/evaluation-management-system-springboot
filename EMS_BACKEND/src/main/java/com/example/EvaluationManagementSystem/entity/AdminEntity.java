package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name="admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String designation;
    private String joiningDate;
    private String gender;
    private String email;
    private String password;
    private Long totalYearsOfExperience;
    private String expertises;
    private String contactNumber;
    private String presentAddress;
    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user;




}
