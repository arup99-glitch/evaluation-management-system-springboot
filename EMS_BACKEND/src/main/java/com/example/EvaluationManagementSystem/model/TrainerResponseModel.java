package com.example.EvaluationManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerResponseModel {
    private String fullName;
    private String email;
    private String password;
    private String designation;
    private Date joiningDate;
    private Long yearsOfExperience;
    private String expertises;
    private Long contactNumber;
    private String presentAddress;
}
