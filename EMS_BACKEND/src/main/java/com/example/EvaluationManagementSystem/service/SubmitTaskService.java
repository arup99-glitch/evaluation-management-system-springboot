package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.entity.SubmitTaskEntity;
import com.example.EvaluationManagementSystem.model.SubmitTaskRequestModel;
import com.example.EvaluationManagementSystem.model.SubmitTaskResponseModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SubmitTaskService {
    ResponseEntity<Object> submitTask(Long taskId,SubmitTaskRequestModel submitTaskRequestModel);
    List<SubmitTaskResponseModel> getTaskSubmissionsById(Long taskId);
    SubmitTaskResponseModel getSubmissionsByIdUnderTask(Long taskId, Long submissionsId);
    ResponseEntity<Object> downloadNoticeFile(Long submissionId);
}
