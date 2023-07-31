package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.AptitudeAndCEOInterviewEntity;
import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.entity.ManagerEvaluationEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.exception.CustomException.BatchNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.MarksAlreadyUploadedException;
import com.example.EvaluationManagementSystem.exception.CustomException.TaskAlreadySubmittedException;
import com.example.EvaluationManagementSystem.exception.CustomException.TraineeNotFoundException;
import com.example.EvaluationManagementSystem.model.AptitudeAndCEOInterviewResponseModel;
import com.example.EvaluationManagementSystem.model.ManagerEvaluationRequestModel;
import com.example.EvaluationManagementSystem.model.ManagerEvaluationResponseModel;
import com.example.EvaluationManagementSystem.repository.CreateBatchRepository;
import com.example.EvaluationManagementSystem.repository.ManagerEvaluationRepository;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.ManagersEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ManagersEvaluationServiceImpl implements ManagersEvaluationService {
    private final TraineeRepository traineeRepository;
    private final ManagerEvaluationRepository managerEvaluationRepository;
    private final CreateBatchRepository batchRepo;
    @Override
    public ResponseEntity<Object> managerEvaluation(ManagerEvaluationRequestModel managersEvaluationRequestModel) {
        TraineeEntity trainee = traineeRepository.findById(managersEvaluationRequestModel.getTraineeId()).orElseThrow(() -> new TraineeNotFoundException("Trainee not found"));

        Optional <ManagerEvaluationEntity> managerEvaluation1 = managerEvaluationRepository.findByTraineeId(trainee.getId());
        if(managerEvaluation1.isPresent()){
            throw new MarksAlreadyUploadedException("This Trainee Marks Already Submitted");
        }

        ManagerEvaluationEntity managerEvaluation = ManagerEvaluationEntity.builder()
                .attendance(managersEvaluationRequestModel.getAttendance())
                .communicationSkill(managersEvaluationRequestModel.getCommunicationSkill())
                .englishLanguageSkill(managersEvaluationRequestModel.getEnglishLanguageSkill())
                .qualityMindset(managersEvaluationRequestModel.getQualityMindset())
                .sincerity(managersEvaluationRequestModel.getSincerity())
                .officeRules(managersEvaluationRequestModel.getOfficeRules())
                .commonScore(managersEvaluationRequestModel.getCommonScore())
                .totalMarks(
                        managersEvaluationRequestModel.getAttendance()+
                        managersEvaluationRequestModel.getQualityMindset()+
                        managersEvaluationRequestModel.getOfficeRules()+
                        managersEvaluationRequestModel.getCommunicationSkill()+
                        managersEvaluationRequestModel.getCommonScore()+
                        managersEvaluationRequestModel.getEnglishLanguageSkill()+
                        managersEvaluationRequestModel.getSincerity())
                .trainee(trainee)
                .build();

        ManagerEvaluationEntity savedManagerEvaluation=managerEvaluationRepository.save(managerEvaluation);
        return new ResponseEntity<>(savedManagerEvaluation, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Object> getAllManagerEvaluationMarks() {
        List<ManagerEvaluationEntity> managerEvaluationMarksLIst = managerEvaluationRepository.findAll();

        List<ManagerEvaluationResponseModel> responseList = new ArrayList<>();
        for(ManagerEvaluationEntity managerEvaluationMarks: managerEvaluationMarksLIst)
        {
            TraineeEntity traineeEntity = traineeRepository.findById(managerEvaluationMarks.getTrainee().getId()).orElseThrow();
            ManagerEvaluationResponseModel managerEvaluationResponseModel = ManagerEvaluationResponseModel.builder()
                    .id(managerEvaluationMarks.getId())
                    .attendance(managerEvaluationMarks.getAttendance())
                    .commonScore(managerEvaluationMarks.getCommonScore())
                    .communicationSkill(managerEvaluationMarks.getCommunicationSkill())
                    .englishLanguageSkill(managerEvaluationMarks.getEnglishLanguageSkill())
                    .officeRules(managerEvaluationMarks.getOfficeRules())
                    .sincerity(managerEvaluationMarks.getSincerity())
                    .qualityMindset(managerEvaluationMarks.getQualityMindset())
                    .totalMarks(managerEvaluationMarks.getTotalMarks())
                    .trainee(traineeEntity)

                    .build();

            responseList.add(managerEvaluationResponseModel);
        }
        return new ResponseEntity<>(responseList,HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Object> getAllManagerEvaluationMarksByBatchId(Long batchId) {

        CreateBatchEntity batch = batchRepo.findById(batchId).get();
        Set<TraineeEntity> trainees = batch.getTrainees();

        List<ManagerEvaluationEntity> managerEvaluations = new ArrayList<>();
        for( TraineeEntity thisTrainee : trainees ){
            managerEvaluations.add( managerEvaluationRepository.findByTrainee(thisTrainee) );
        }

        return new ResponseEntity<>( managerEvaluations , HttpStatus.OK);
    }
}
