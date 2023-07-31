package com.example.EvaluationManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TraineeResponseModel {
    private Long id;
    private String fullName;
    private String degreeName;
    private String educationalInstitute;
    private Double cgpa;
    private Date passingYear;
    private String presentAddress;
    private String contactNumber;
    private Date dateOfBirth;
    private String gender;
    private String email;
    private String password;
}
