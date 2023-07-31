package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="trainer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrainerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String designation;
    private Date joiningDate;
    private Long yearsOfExperience;
    private String expertises;
    private Long contactNumber;
    private String presentAddress;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;


}
