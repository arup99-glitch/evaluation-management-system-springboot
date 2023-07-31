package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.model.CreateBatchRequestModel;
import com.example.EvaluationManagementSystem.model.TraineeRequestModel;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface CreateBatchService {
    ResponseEntity<Object> createBatch(CreateBatchRequestModel createBatchRequestModel);
    ResponseEntity<Object> getAllBatches();
    ResponseEntity<Object> getAllBatchName();
    ResponseEntity<Object> getBatchByid(Long batchId);

    ResponseEntity<Set<TraineeEntity>> findTraineeUnderBatchById(Long batchId);
    void deleteBatch(Long batchId);
}
