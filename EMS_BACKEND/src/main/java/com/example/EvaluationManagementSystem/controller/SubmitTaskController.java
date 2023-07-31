package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.model.SubmitTaskRequestModel;
import com.example.EvaluationManagementSystem.model.SubmitTaskResponseModel;
import com.example.EvaluationManagementSystem.repository.SubmitTaskRepository;
import com.example.EvaluationManagementSystem.service.SubmitTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class SubmitTaskController {
    private final SubmitTaskService submitTaskService;
    private final SubmitTaskRepository submitTaskRepository;

    @PostMapping("/submit/{taskId}")
    public ResponseEntity<Object> submitTask(
            @PathVariable Long taskId,
            @ModelAttribute SubmitTaskRequestModel submitTaskRequestModel) {
        return submitTaskService.submitTask(taskId,submitTaskRequestModel);
    }

    @GetMapping("/submission/{taskId}")
    public ResponseEntity<List<SubmitTaskResponseModel>> getSubmissionsByTaskId(@PathVariable Long taskId) {
        return ResponseEntity.ok(submitTaskService.getTaskSubmissionsById(taskId));
    }
    @GetMapping("/{taskId}/submissions/{submissionsId}")
    public ResponseEntity<?> getSubmissionsByIdUnderTask(@PathVariable Long taskId,@PathVariable Long submissionsId) {
        SubmitTaskResponseModel model=submitTaskService.getSubmissionsByIdUnderTask(taskId,submissionsId);
        return ResponseEntity.ok(model);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<Object> downloadNoticeFile(@PathVariable("id") Long submissionId) {
        return submitTaskService.downloadNoticeFile(submissionId);
    }
}

