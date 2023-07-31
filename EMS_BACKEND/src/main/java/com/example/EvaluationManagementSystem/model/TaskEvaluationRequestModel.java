package com.example.EvaluationManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskEvaluationRequestModel {
    private String taskType;
    private Long taskId;
    private Long traineeId;
    private Double requirementUnderstanding;
    private Double expectedOutput;
    private Double codeQuality;
    private Double demonstration;
    private Double codeUnderstanding;
    private Double totalMarks;
}
