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
public class CreateTaskRequestModel {
    private String taskName;
    private Date startingDate;
    private Date deadline;
    private String taskType;
    private Long batchId;
}
