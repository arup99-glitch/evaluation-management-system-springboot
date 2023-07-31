package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.model.AptitudeAndCEOInterviewRequestModel;
import com.example.EvaluationManagementSystem.model.CreateBatchRequestModel;
import com.example.EvaluationManagementSystem.service.AptitudeAndCEOInterviewService;
import com.example.EvaluationManagementSystem.service.CreateBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/CEOEvaluation")
@RequiredArgsConstructor
public class AptitudeAndCEOInterviewController {
    private final AptitudeAndCEOInterviewService aptitudeAndCEOInterviewService;
    @PostMapping("/upload")
    public ResponseEntity<Object>aptitudeTestMarksUpload (@RequestBody AptitudeAndCEOInterviewRequestModel aptitudeAndCEOInterviewRequestModel){
        return aptitudeAndCEOInterviewService.aptitudeAndCeoInterviewEvaluation(aptitudeAndCEOInterviewRequestModel);
    }
    @GetMapping("/allMarks")
    public ResponseEntity<Object> getAllCEOEvaluationMarks(){
        return aptitudeAndCEOInterviewService.getAllAptitudeAndCEOInterviewMarks();
    }
    @GetMapping("/allMarks/{batchId}")
    public ResponseEntity<Object> getAllCEOEvaluationMarks(@PathVariable Long batchId){
        return aptitudeAndCEOInterviewService.getAllCEOEvaluationMarksByBatchId(batchId);
    }
}
