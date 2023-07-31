package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.model.CreateTaskRequestModel;
import com.example.EvaluationManagementSystem.service.CreateTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@RequiredArgsConstructor
public class CreateTaskController {
    private final CreateTaskService createTaskService;
    @PostMapping("/create")
    public ResponseEntity<Object> createDailyTask (@RequestBody CreateTaskRequestModel createTaskRequestModel) {
        return createTaskService.createDailyTask(createTaskRequestModel);
    }

    @GetMapping("/batch/{batchId}")
    public ResponseEntity<List<Object>> getTaskById(@PathVariable Long batchId) {
        return createTaskService.taskUnderBatchId(batchId);
    }
    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        createTaskService.deleteTask(taskId);
    }


}
