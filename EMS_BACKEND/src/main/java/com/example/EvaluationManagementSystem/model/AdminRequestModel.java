package com.example.EvaluationManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRequestModel {
    private Long id;
    private String email;
    private String password;
    private String fullName;
    private String designation;
    private String joiningDate;
    private String gender;
    private Long totalYearsOfExperience;
    private String expertises;
    private String contactNumber;
    private String presentAddress;
}
