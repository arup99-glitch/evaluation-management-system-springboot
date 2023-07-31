package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="trainee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String gender;
    private Date dateOfBirth;
    private String degreeName;
    private String educationalInstitute;
    private Double cgpa;
    private Date passingYear;
    private String presentAddress;
    private String fullName;
    private String contactNumber;


    @OneToOne (cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private UserEntity user;







}
