package com.example.EvaluationManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManagerEvaluationRequestModel {

    private Long traineeId;
    private Double commonScore;
    private Double officeRules;
    private Double sincerity;
    private Double qualityMindset;
    private Double attendance;
    private Double communicationSkill;
    private Double englishLanguageSkill;

}
