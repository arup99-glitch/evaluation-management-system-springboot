package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.model.TaskEvaluationRequestModel;
import org.springframework.http.ResponseEntity;

public interface TaskEvaluationService {
    ResponseEntity<Object> taskEvaluation(TaskEvaluationRequestModel taskEvaluationRequestModel);
    ResponseEntity<Object> getTaskEvaluationMarks();
}
