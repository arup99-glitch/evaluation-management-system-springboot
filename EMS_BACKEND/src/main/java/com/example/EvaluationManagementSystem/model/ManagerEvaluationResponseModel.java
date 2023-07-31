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
public class ManagerEvaluationResponseModel {
    private Long id;
    private TraineeEntity trainee;
    private Double commonScore;
    private Double officeRules;
    private Double sincerity;
    private Double qualityMindset;
    private Double attendance;
    private Double communicationSkill;
    private Double englishLanguageSkill;
    private Double totalMarks;
}
