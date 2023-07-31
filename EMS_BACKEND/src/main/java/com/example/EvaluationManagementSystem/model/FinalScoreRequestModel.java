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
public class FinalScoreRequestModel {
    private Double dailyTask;
    private Double miniProject;
    private Double midProject;
    private Double finalProject;
    private Double managerEvaluation;
    private Double ceoEvaluation;


}
