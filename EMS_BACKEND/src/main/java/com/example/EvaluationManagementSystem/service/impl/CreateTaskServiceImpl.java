package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.entity.CreateTaskEntity;
import com.example.EvaluationManagementSystem.exception.CustomException.BatchNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.TaskNotFoundException;
import com.example.EvaluationManagementSystem.model.CreateTaskRequestModel;
import com.example.EvaluationManagementSystem.model.CreateTaskResponseModel;
import com.example.EvaluationManagementSystem.repository.CreateBatchRepository;
import com.example.EvaluationManagementSystem.repository.CreateTaskRepository;
import com.example.EvaluationManagementSystem.service.CreateTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateTaskServiceImpl implements CreateTaskService {
    @Autowired
    private final CreateBatchRepository createBatchRepository;
    private final CreateTaskRepository createTaskRepository;

    @Override
    public ResponseEntity<Object> createDailyTask(CreateTaskRequestModel createTaskRequestModel) {
        CreateBatchEntity createBatch = createBatchRepository.findById(createTaskRequestModel.getBatchId()).orElseThrow();

        CreateTaskEntity createTask= CreateTaskEntity.builder()
                .taskName(createTaskRequestModel.getTaskName())
                .startingDate(createTaskRequestModel.getStartingDate())
                .deadline(createTaskRequestModel.getDeadline())
                .taskType(createTaskRequestModel.getTaskType())
                .batchId(createBatch.getId())
                .build();

        createTask = createTaskRepository.save(createTask);
        createBatch.getCreateTaskEntities().add(createTask);
        createBatchRepository.save(createBatch);
        return new ResponseEntity<>(createTask, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<Object>> taskUnderBatchId(Long batchId) {
        Optional<CreateBatchEntity> batch = createBatchRepository.findById(batchId);

        if (batch.isEmpty()) {
            throw new BatchNotFoundException("Batch not found for batchId: " + batchId);
        }

        List<CreateTaskEntity> tasks = batch.get().getCreateTaskEntities();

        List<Object> responseList = new ArrayList<>();
        String batchName = batch.get().getBatchName();

        for (CreateTaskEntity task : tasks) {
            CreateTaskResponseModel taskResponseModel = CreateTaskResponseModel.builder()
                    .id(task.getId())
                    .taskName(task.getTaskName())
                    .taskName(task.getTaskName())
                    .startingDate(task.getStartingDate())
                    .taskType(task.getTaskType())
                    .deadline(task.getDeadline())
                    .batchId(task.getBatchId())
                    .batchName(batchName)
                    .build();

            responseList.add(taskResponseModel);
        }

        return ResponseEntity.ok(responseList);
    }

    @Override
    public void deleteTask(Long taskId) {
        Optional<CreateTaskEntity> task = createTaskRepository.findById(taskId);
        if(task.isPresent()){
            createTaskRepository.delete(task.orElseThrow());
        }
        else {
            throw new TaskNotFoundException("Task Id  is not found.");
        }
    }


}
