package com.example.EvaluationManagementSystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTaskResponseModel {
    private Long submissionId;
    private Long traineeId;
    private Date date;
    private String traineeName;
    private String FileUpload;



}
