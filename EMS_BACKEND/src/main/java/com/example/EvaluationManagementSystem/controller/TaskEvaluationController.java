package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.model.AptitudeAndCEOInterviewRequestModel;
import com.example.EvaluationManagementSystem.model.TaskEvaluationRequestModel;
import com.example.EvaluationManagementSystem.service.AptitudeAndCEOInterviewService;
import com.example.EvaluationManagementSystem.service.TaskEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taskEvaluation")
@RequiredArgsConstructor
public class TaskEvaluationController {
    private final TaskEvaluationService taskEvaluationService;
    @PostMapping("/marks/upload")
    public ResponseEntity<Object> taskEvaluationMarksUpload (@RequestBody TaskEvaluationRequestModel taskEvaluationRequestModel){
        return taskEvaluationService.taskEvaluation(taskEvaluationRequestModel);
    }
    @GetMapping("/allMarks")
    public ResponseEntity<Object> getAllTaskEvaluationMarks(){
        return taskEvaluationService.getTaskEvaluationMarks();
    }
}
