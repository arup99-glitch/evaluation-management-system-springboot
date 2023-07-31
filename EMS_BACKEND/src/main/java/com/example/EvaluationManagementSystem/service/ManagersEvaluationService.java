package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.model.ManagerEvaluationRequestModel;
import org.springframework.http.ResponseEntity;

public interface ManagersEvaluationService {
    ResponseEntity<Object> managerEvaluation(ManagerEvaluationRequestModel managersEvaluationRequestModel);
    ResponseEntity<Object> getAllManagerEvaluationMarks();
    ResponseEntity<Object> getAllManagerEvaluationMarksByBatchId(Long batchId);
}
