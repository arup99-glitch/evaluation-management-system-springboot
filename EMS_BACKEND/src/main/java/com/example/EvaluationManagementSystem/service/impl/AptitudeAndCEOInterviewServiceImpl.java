package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.AptitudeAndCEOInterviewEntity;
import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.entity.ManagerEvaluationEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.exception.CustomException.MarksAlreadyUploadedException;
import com.example.EvaluationManagementSystem.exception.CustomException.TraineeNotFoundException;
import com.example.EvaluationManagementSystem.model.AptitudeAndCEOInterviewRequestModel;
import com.example.EvaluationManagementSystem.model.AptitudeAndCEOInterviewResponseModel;
import com.example.EvaluationManagementSystem.repository.AptitudeAndCEOInterviewRepository;
import com.example.EvaluationManagementSystem.repository.CreateBatchRepository;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.AptitudeAndCEOInterviewService;
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
public class AptitudeAndCEOInterviewServiceImpl implements AptitudeAndCEOInterviewService {
    private final TraineeRepository traineeRepository;
    private final AptitudeAndCEOInterviewRepository aptitudeAndCEOInterviewRepository;
    private final CreateBatchRepository batchRepository;
    @Override
    public ResponseEntity<Object> aptitudeAndCeoInterviewEvaluation(AptitudeAndCEOInterviewRequestModel aptitudeAndCEOInterviewRequestModel) {
        TraineeEntity trainee = traineeRepository.findById(aptitudeAndCEOInterviewRequestModel.getTraineeId()).orElseThrow(() -> new TraineeNotFoundException("Trainee not found"));
        Optional<AptitudeAndCEOInterviewEntity> aptitudeAndCEOInterview = aptitudeAndCEOInterviewRepository.findByTraineeId(trainee.getId());
        if(aptitudeAndCEOInterview.isPresent()){
           throw new MarksAlreadyUploadedException("This Trainee Marks Already Uploaded");

        }
        AptitudeAndCEOInterviewEntity aptitudeAndCEOInterviewEntity = AptitudeAndCEOInterviewEntity.builder()
                .hrInterview(aptitudeAndCEOInterviewRequestModel.getHrInterview())
                .aptitudeTest(aptitudeAndCEOInterviewRequestModel.getAptitudeTest())
                .totalScore(
                        aptitudeAndCEOInterviewRequestModel.getHrInterview()+
                        aptitudeAndCEOInterviewRequestModel.getAptitudeTest()
                )
                .trainee(trainee)
                .build();

        AptitudeAndCEOInterviewEntity savedAptitudeAndCEOInterview = aptitudeAndCEOInterviewRepository.save(aptitudeAndCEOInterviewEntity);
        return new ResponseEntity<>(savedAptitudeAndCEOInterview, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Object> getAllAptitudeAndCEOInterviewMarks() {
        List<AptitudeAndCEOInterviewEntity> AptitudeAndCEOInterviewMarksList = aptitudeAndCEOInterviewRepository.findAll();

        List<AptitudeAndCEOInterviewResponseModel> responseList = new ArrayList<>();
        for(AptitudeAndCEOInterviewEntity aptitudeAndCEOMarks: AptitudeAndCEOInterviewMarksList)
        {
            TraineeEntity traineeEntity = traineeRepository.findById(aptitudeAndCEOMarks.getTrainee().getId()).orElseThrow();
            AptitudeAndCEOInterviewResponseModel aptitudeAndCEOInterviewResponseModel = AptitudeAndCEOInterviewResponseModel.builder()
                    .aptitudeTest(aptitudeAndCEOMarks.getAptitudeTest())
                    .hrInterview(aptitudeAndCEOMarks.getHrInterview())
                    .totalScore(aptitudeAndCEOMarks.getTotalScore())
                    .trainee(traineeEntity)
                    .build();
            responseList.add(aptitudeAndCEOInterviewResponseModel);
        }
        return new ResponseEntity<>(responseList,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAllCEOEvaluationMarksByBatchId(Long batchId) {
        CreateBatchEntity batch = batchRepository.findById(batchId).get();
        Set<TraineeEntity> trainees = batch.getTrainees();

        List<AptitudeAndCEOInterviewEntity> ceoEvaluations = new ArrayList<>();
        for( TraineeEntity thisTrainee : trainees ){
            ceoEvaluations.add( aptitudeAndCEOInterviewRepository.findByTrainee(thisTrainee) );
        }

        return new ResponseEntity<>( ceoEvaluations , HttpStatus.OK);
    }


}
