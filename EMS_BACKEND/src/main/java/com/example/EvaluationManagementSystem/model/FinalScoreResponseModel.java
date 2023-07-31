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
public class FinalScoreResponseModel {
    private Double dailyTask;
    private Double miniProject;
    private Double midProject;
    private Double finalProject;
    private Double managerEvaluation;
    private Double CEOEvaluation;
    private Double finalScore;
    private TraineeEntity trainee;
}
