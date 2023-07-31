package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.model.AptitudeAndCEOInterviewRequestModel;
import org.springframework.http.ResponseEntity;

public interface AptitudeAndCEOInterviewService {
    ResponseEntity<Object> aptitudeAndCeoInterviewEvaluation(AptitudeAndCEOInterviewRequestModel aptitudeAndCEOInterviewRequestModel);
    ResponseEntity<Object> getAllAptitudeAndCEOInterviewMarks();
    ResponseEntity<Object> getAllCEOEvaluationMarksByBatchId(Long batchId);
}
