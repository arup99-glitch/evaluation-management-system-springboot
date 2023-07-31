package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.*;
import com.example.EvaluationManagementSystem.exception.CustomException.MarksAlreadyUploadedException;
import com.example.EvaluationManagementSystem.model.FinalScoreRequestModel;
import com.example.EvaluationManagementSystem.repository.*;
import com.example.EvaluationManagementSystem.service.FinalScoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class FinalScoreServiceImpl implements FinalScoreService {
    private final CreateBatchRepository batchRepository;
    private final TraineeRepository traineeRepository;
    private final TaskEvaluationRepository taskEvaluationRepository;
    private final ManagerEvaluationRepository managerEvaluationRepository;
    private final AptitudeAndCEOInterviewRepository aptitudeAndCEOInterviewRepository;
    private final FinalScoreRepository finalScoreRepository;

    @Override
    public ResponseEntity<List<Object>> finalScoreFullBatch(Long batchId, FinalScoreRequestModel finalScoreRequestModel) {
        CreateBatchEntity batch = batchRepository.findById(batchId).get();
        Set<TraineeEntity> trainees = batch.getTrainees();

        List<Object> saveFinalScore = new ArrayList<>();

        for(TraineeEntity trainee : trainees){

            if(finalScoreRepository.findByTraineeEntity(trainee) != null){

                throw new MarksAlreadyUploadedException("Marks Already Uploaded");
            }

            List<TaskEvaluationEntity> taskEvaluation = taskEvaluationRepository.findByTrainee(trainee);
            ManagerEvaluationEntity managerEvaluation = managerEvaluationRepository.findByTrainee(trainee);
            AptitudeAndCEOInterviewEntity aptitudeAndCEOInterview = aptitudeAndCEOInterviewRepository.findByTrainee(trainee);

            Double dailyTask = 0.00;
            Double miniProject = 0.00;
            Double midProject =0.00;
            Double finalProject = 0.00;

            for(TaskEvaluationEntity taskEvaluationTotalMarks : taskEvaluation){
                if(taskEvaluationTotalMarks.getTaskType().equalsIgnoreCase("Daily Task")){
                    dailyTask += taskEvaluationTotalMarks.getTotalMarks();
                }
                else if (taskEvaluationTotalMarks.getTaskType().equalsIgnoreCase("Mini Project")) {
                    miniProject += taskEvaluationTotalMarks.getTotalMarks();
                }
                else if (taskEvaluationTotalMarks.getTaskType().equalsIgnoreCase("Mid Project")) {
                    midProject += taskEvaluationTotalMarks.getTotalMarks();
                }
                else{
                    finalProject += taskEvaluationTotalMarks.getTotalMarks();
                }
            }

            Double TotalMarks =
                          (dailyTask * (finalScoreRequestModel.getDailyTask()))+
                          (miniProject * (finalScoreRequestModel.getMiniProject()))+
                          (midProject * (finalScoreRequestModel.getMidProject()))+
                                  (finalProject * (finalScoreRequestModel.getFinalProject()))+
                          (managerEvaluation.getTotalMarks() * (finalScoreRequestModel.getManagerEvaluation())) +
                          (aptitudeAndCEOInterview.getTotalScore() * (finalScoreRequestModel.getCeoEvaluation()));

            FinalScoreEntity totalFinalScore = FinalScoreEntity.builder()
                    .dailyMarks(dailyTask)
                    .miniProject(miniProject)
                    .midProject(midProject)
                    .finalProject(finalProject)
                    .managerEvaluation(managerEvaluation.getTotalMarks())
                    .ceoHrInterview(aptitudeAndCEOInterview.getTotalScore())
                    .totalMarks(TotalMarks)
                    .traineeEntity(trainee)
                    .build();
            saveFinalScore.add(finalScoreRepository.save(totalFinalScore));

        }
        return new ResponseEntity<>(saveFinalScore, HttpStatus.CREATED);


    }

    @Override
    public ResponseEntity<List<Object>> viewAllMarksUnderBatchId(Long batchId) {
        List<Object> finalMarks = new ArrayList<>();
        CreateBatchEntity createBatch = batchRepository.findById(batchId).get();
        for(TraineeEntity trainee : createBatch.getTrainees()){
            FinalScoreEntity finalScoreEntity = finalScoreRepository.findByTraineeEntity(trainee);
            finalMarks.add(finalScoreEntity);
        }
        return new ResponseEntity<>(finalMarks,HttpStatus.OK);

    }
}