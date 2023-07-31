package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.service.AssignTraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainee")
@RequiredArgsConstructor
public class AssignTraineeController {
    private final AssignTraineeService assignTraineeService;

    @PostMapping("assign/batch/{batchId}")
    public ResponseEntity<String> addTraineesToBatch(
            @PathVariable Long batchId,
            @RequestBody List<Long> traineeIds) {
        System.out.println("Received batchId: " + batchId);
        System.out.println("Received traineeIds: " + traineeIds);
        try {
            assignTraineeService.addTraineesToBatch(batchId,traineeIds);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Trainees added to batch successfully");
    }

}
