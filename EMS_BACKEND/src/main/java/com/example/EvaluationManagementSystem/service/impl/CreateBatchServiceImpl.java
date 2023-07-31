package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.entity.CreateTaskEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.exception.CustomException.BatchNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.TraineeNotFoundException;
import com.example.EvaluationManagementSystem.model.CreateBatchRequestModel;
import com.example.EvaluationManagementSystem.model.CreateBatchResponseModel;
import com.example.EvaluationManagementSystem.model.TaskEvaluationResponseModel;
import com.example.EvaluationManagementSystem.model.TraineeResponseModel;
import com.example.EvaluationManagementSystem.repository.CreateBatchRepository;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.CreateBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CreateBatchServiceImpl implements CreateBatchService {
    @Autowired
    private final CreateBatchRepository batchRepository;
    private final TraineeRepository traineeRepository;

    @Override
    public  ResponseEntity<Object>  createBatch(CreateBatchRequestModel createBatchRequestModel) {
        CreateBatchEntity createBatchEntity = CreateBatchEntity.builder()
                .batchName(createBatchRequestModel.getBatchName())
                .description(createBatchRequestModel.getDescription())
                .startingDate(createBatchRequestModel.getStartingDate())
                .endingDate(createBatchRequestModel.getEndingDate())
                .build();
        CreateBatchEntity savedCreateBatch = batchRepository.save(createBatchEntity);
        CreateBatchResponseModel createBatchResponseModel = CreateBatchResponseModel.builder()
                .id(savedCreateBatch.getId())
                .batchName(savedCreateBatch.getBatchName())
                .description(savedCreateBatch.getDescription())
                .startingDate(savedCreateBatch.getStartingDate())
                .endingDate(savedCreateBatch.getEndingDate())
                .build();
        return new ResponseEntity<>(createBatchResponseModel, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Object> getAllBatches() {
        List<CreateBatchEntity> BatchList = batchRepository.findAll();
        List<CreateBatchResponseModel> responseList = new ArrayList<>();
        for(CreateBatchEntity batch: BatchList)
        {
            Set<TraineeEntity> trainees = batch.getTrainees();
            List<Long> traineeIds = trainees.stream().map(TraineeEntity::getId).collect(Collectors.toList());

            List<TraineeResponseModel> traineeResponseList = new ArrayList<>();
            for (TraineeEntity trainee : trainees) {
                TraineeResponseModel traineeResponse = TraineeResponseModel.builder()
                        .id(trainee.getId())
                        .fullName(trainee.getFullName())
                        .email(trainee.getEmail())
                        .contactNumber(trainee.getContactNumber())
                        .presentAddress(trainee.getPresentAddress())
                        .build();

                traineeResponseList.add(traineeResponse);
            }
            CreateBatchResponseModel batchResponseModel = CreateBatchResponseModel.builder()
                    .id(batch.getId())
                    .batchName(batch.getBatchName())
                    .description(batch.getDescription())
                    .startingDate(batch.getStartingDate())
                    .endingDate(batch.getEndingDate())
                    .totalTrainee(trainees.size())
                    .trainees(traineeResponseList)
                    .build();
            responseList.add(batchResponseModel);
        }
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllBatchName() {
        List<CreateBatchEntity> BatchList = batchRepository.findAll();
        List<Map<String,Object>> batches = new ArrayList<>();
        for(CreateBatchEntity batch: BatchList){
            Map<String,Object> batchData = new HashMap<>();
            batchData.put("id",batch.getId());
            batchData.put("name",batch.getBatchName());
            batches.add(batchData);

        }
         Map<String,Object> response = new HashMap<>();
        response.put("Total Batch",batches.size());
        response.put("Batches",batches);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getBatchByid(Long batchId) {
        return new ResponseEntity<>(batchRepository.findById(batchId)
                   .orElseThrow(() -> new BatchNotFoundException("Batch id is not found")),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Set<TraineeEntity>> findTraineeUnderBatchById(Long batchId) {

        Set<TraineeEntity> trainees = batchRepository.findById(batchId).get().getTrainees();
        return new ResponseEntity<>(trainees , HttpStatus.OK);
    }

    @Override
    public void deleteBatch(Long batchId) {
        Optional<CreateBatchEntity> batch = batchRepository.findById(batchId);
        if(batch.isPresent()){
            batchRepository.delete(batch.orElseThrow());
        }
        else {
            throw new BatchNotFoundException("Batch is not found.");
        }
    }
}



