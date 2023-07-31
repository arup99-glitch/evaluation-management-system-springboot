package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.model.ManagerEvaluationRequestModel;
import com.example.EvaluationManagementSystem.service.ManagersEvaluationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/managerEvaluation")
@RequiredArgsConstructor
public class ManagerEvaluationController {
    private final ManagersEvaluationService managersEvaluationService;
    @PostMapping("/marks/upload")//okk
    public ResponseEntity<Object> managersEvaluationMarksUpload (@RequestBody ManagerEvaluationRequestModel managersEvaluationRequestModel){
        return managersEvaluationService.managerEvaluation(managersEvaluationRequestModel);
    }
    @GetMapping("/allMarks")
    public ResponseEntity<Object> getAllManagerEvaluationMarks(){
        return managersEvaluationService.getAllManagerEvaluationMarks();
    }
    @GetMapping("/allMarks/{batchId}")
    public ResponseEntity<Object> getAllManagerEvaluationMarks(@PathVariable Long batchId){
        return managersEvaluationService.getAllManagerEvaluationMarksByBatchId(batchId);
    }

}
