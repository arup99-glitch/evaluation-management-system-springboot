package com.example.EvaluationManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AptitudeAndCEOInterviewRequestModel {

    private Long traineeId;
    private Double aptitudeTest;
    private Double hrInterview;

}
