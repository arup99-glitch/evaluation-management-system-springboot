package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.model.FinalScoreRequestModel;
import com.example.EvaluationManagementSystem.model.FinalScoreResponseModel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface FinalScoreService {
    ResponseEntity<List<Object>> finalScoreFullBatch(Long batchId, FinalScoreRequestModel finalScoreRequestModel);
    ResponseEntity<List<Object>>viewAllMarksUnderBatchId(Long batchId);

}
