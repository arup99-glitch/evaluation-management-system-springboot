package com.example.EvaluationManagementSystem.model;

import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBatchResponseModel {
    private Long id;
    private String batchName;
    private Date startingDate;
    private Date endingDate;
    private String description;
    private int totalTrainee;
    public List<Long> trainee;
    public List<TraineeResponseModel> trainees; // Add list of trainees
}
