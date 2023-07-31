package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.*;
import com.example.EvaluationManagementSystem.model.TaskEvaluationRequestModel;
import com.example.EvaluationManagementSystem.model.TaskEvaluationResponseModel;
import com.example.EvaluationManagementSystem.repository.CreateTaskRepository;
import com.example.EvaluationManagementSystem.repository.SubmitTaskRepository;
import com.example.EvaluationManagementSystem.repository.TaskEvaluationRepository;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.TaskEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskEvaluationServiceImpl implements TaskEvaluationService {
    private final TaskEvaluationRepository taskEvaluationRepository;
    private final TraineeRepository traineeRepository;
    private final SubmitTaskRepository submitTaskRepository;
    private final CreateTaskRepository createTaskRepository;
    @Override
    public ResponseEntity<Object> taskEvaluation(TaskEvaluationRequestModel taskEvaluationRequestModel) {
        TraineeEntity trainee = traineeRepository.findById(taskEvaluationRequestModel.getTraineeId()).orElseThrow();
        CreateTaskEntity dailyTask = createTaskRepository.findById(taskEvaluationRequestModel.getTaskId()).orElseThrow();
        Optional<TaskEvaluationEntity> taskEvaluation1 = taskEvaluationRepository.findByCreateDailyTaskIdAndTraineeId(dailyTask.getId(), trainee.getId());
        if(taskEvaluation1.isPresent()){
            return new ResponseEntity<>("This Task Marks Already Uploaded for this specific trainee",HttpStatus.NOT_ACCEPTABLE);

        }

        TaskEvaluationEntity taskEvaluation = TaskEvaluationEntity.builder()
                .taskType(taskEvaluationRequestModel.getTaskType())
                .codeQuality(taskEvaluationRequestModel.getCodeQuality())
                .demonstration(taskEvaluationRequestModel.getDemonstration())
                .codeUnderstanding(taskEvaluationRequestModel.getCodeUnderstanding())
                .requirementUnderstanding(taskEvaluationRequestModel.getRequirementUnderstanding())
                .expectedOutput(taskEvaluationRequestModel.getExpectedOutput())
                .totalMarks(
                                taskEvaluationRequestModel.getCodeQuality() +
                                taskEvaluationRequestModel.getDemonstration()+
                                taskEvaluationRequestModel.getCodeUnderstanding()+
                                taskEvaluationRequestModel.getRequirementUnderstanding()+
                                taskEvaluationRequestModel.getExpectedOutput()

                )
                .trainee(trainee)
                .createDailyTask(dailyTask)
                .build();
        TaskEvaluationEntity savedTaskEvaluation = taskEvaluationRepository.save(taskEvaluation);
        return new ResponseEntity<>(savedTaskEvaluation, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> getTaskEvaluationMarks() {

        List<TaskEvaluationEntity> TaskEvaluationMarksList = taskEvaluationRepository.findAll();

        List<TaskEvaluationResponseModel> responseList = new ArrayList<>();

        for(TaskEvaluationEntity taskEvaluationMarks: TaskEvaluationMarksList)
        {

           TraineeEntity traineeEntity = traineeRepository.findById(taskEvaluationMarks.getTrainee().getId()).orElseThrow();
           CreateTaskEntity taskEntity = createTaskRepository.findById(taskEvaluationMarks.getCreateDailyTask().getId()).orElseThrow();
            TaskEvaluationResponseModel taskEvaluationResponseModel = TaskEvaluationResponseModel.builder()

                    .taskType(taskEvaluationMarks.getTaskType())
                    .requirementUnderstanding(taskEvaluationMarks.getRequirementUnderstanding())
                    .codeUnderstanding(taskEvaluationMarks.getCodeUnderstanding())
                    .codeQuality(taskEvaluationMarks.getCodeQuality())
                    .demonstration(taskEvaluationMarks.getDemonstration())
                    .expectedOutput(taskEvaluationMarks.getExpectedOutput())
                    .totalMarks(taskEvaluationMarks.getTotalMarks())
                    .task(taskEntity)
                    .trainee(traineeEntity)
                    .build();
            responseList.add(taskEvaluationResponseModel);
        }
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }
}
