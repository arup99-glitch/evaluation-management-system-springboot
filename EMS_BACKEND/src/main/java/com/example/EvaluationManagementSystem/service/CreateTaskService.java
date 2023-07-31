package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.model.CreateTaskRequestModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CreateTaskService {
    ResponseEntity<Object> createDailyTask(CreateTaskRequestModel createTaskRequestModel);
    ResponseEntity<List<Object>>taskUnderBatchId(Long batchId);
    void deleteTask(Long taskId);

}
