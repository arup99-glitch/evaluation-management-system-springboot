package com.example.EvaluationManagementSystem.model;

import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AptitudeAndCEOInterviewResponseModel {
    private TraineeEntity trainee;
    private Double aptitudeTest;
    private Double hrInterview;
    private Double totalScore;
}
