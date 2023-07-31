package com.example.EvaluationManagementSystem.service;

import java.util.List;

public interface AssignTraineeService {
    void addTraineesToBatch(Long batchId, List<Long> traineeIds);


}
