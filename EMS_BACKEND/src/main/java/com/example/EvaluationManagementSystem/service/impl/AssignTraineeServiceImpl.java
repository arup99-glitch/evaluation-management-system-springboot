package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.exception.CustomException.BatchNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.TraineeNotFoundException;
import com.example.EvaluationManagementSystem.repository.CreateBatchRepository;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.AssignTraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AssignTraineeServiceImpl implements AssignTraineeService {
    private final CreateBatchRepository createBatchRepository;
    private final TraineeRepository traineeRepository;

@Override
@Transactional
public void addTraineesToBatch(Long batchId, List<Long> traineeIds) {
    Optional<CreateBatchEntity> batchOptional = createBatchRepository.findById(batchId);
    if (batchOptional.isPresent()) {
        CreateBatchEntity batch = batchOptional.get();
        for (Long traineeId : traineeIds) {
            Optional<TraineeEntity> traineeOptional = traineeRepository.findById(traineeId);
            if (traineeOptional.isPresent()) {
                TraineeEntity trainee = traineeOptional.get();
                batch.getTrainees().add(trainee);
            } else {
                throw new TraineeNotFoundException("Trainee Not Found");

            }
        }
        createBatchRepository.save(batch);
    } else {
       throw new BatchNotFoundException("Batch Not Found");

    }
}

}
