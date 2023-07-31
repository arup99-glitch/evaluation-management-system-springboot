package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.CreateTaskEntity;
import com.example.EvaluationManagementSystem.entity.SubmitTaskEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.exception.CustomException.SubmitTaskNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.TaskAlreadySubmittedException;
import com.example.EvaluationManagementSystem.model.SubmitTaskRequestModel;
import com.example.EvaluationManagementSystem.model.SubmitTaskResponseModel;
import com.example.EvaluationManagementSystem.repository.CreateTaskRepository;
import com.example.EvaluationManagementSystem.repository.SubmitTaskRepository;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.SubmitTaskService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubmitTaskServiceImpl implements SubmitTaskService {

    private final SubmitTaskRepository submitTaskRepository;
    private final CreateTaskRepository createTaskRepository;
    private final TraineeRepository traineeRepository;
    private final String TASK_UPLOAD_DIR = "D:\\ALL TaskSubmission";
    private final String TASK_DOWNLOAD_DIR = "D:\\Task DOWNLOAD";

    @Override
    public ResponseEntity<Object> submitTask(Long taskId,SubmitTaskRequestModel submitTaskRequestModel) {
        MultipartFile file = submitTaskRequestModel.getFileUpload();
        String fileUrl = null;
        if (file != null && !file.isEmpty()) {
            fileUrl = uploadFile(file,TASK_UPLOAD_DIR );
        }


        CreateTaskEntity createDailyTask = createTaskRepository.findById(taskId).orElseThrow();
        TraineeEntity traineeEntity=traineeRepository.findById(submitTaskRequestModel.getTraineeId()).orElseThrow();
        Optional<SubmitTaskEntity> submitTaskEntity1=submitTaskRepository.findByTraineeEntityIdAndTaskId(traineeEntity.getId(),createDailyTask.getId());
        if(submitTaskEntity1.isPresent()){
            throw new TaskAlreadySubmittedException("You Already Submitted this Task");
        }

        SubmitTaskEntity submitTaskEntity = SubmitTaskEntity.builder()
                .traineeEntity(traineeEntity)
                .task(createDailyTask)
                .date(submitTaskRequestModel.getDate())


                .build();
                if (fileUrl != null) {
                    submitTaskEntity.setFileUrl(fileUrl);
        }


        SubmitTaskEntity savedSubmitTask = submitTaskRepository.save(submitTaskEntity);
        return new ResponseEntity<>(savedSubmitTask, HttpStatus.CREATED);
    }
    public static String uploadFile(MultipartFile file, String UPLOAD_DIR) {
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = StringUtils.cleanPath(file.getOriginalFilename());
                File destinationDir = new File(UPLOAD_DIR);
                if (!destinationDir.exists()) {
                    destinationDir.mkdirs(); // Create the directory if it doesn't exist
                }
                File destinationFile = new File(destinationDir, fileName);
                file.transferTo(destinationFile);
                return destinationFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    public ResponseEntity<Object> downloadNoticeFile(Long submissionId) {
        SubmitTaskEntity submitTaskEntity = submitTaskRepository.findById(submissionId).orElseThrow(() -> new SubmitTaskNotFoundException("Your Submission File was not found " ));
        if(submitTaskEntity.getFileUrl()==null){
            throw new SubmitTaskNotFoundException("File Not Found");
        }

        try{
            File file = new File(submitTaskEntity.getFileUrl());
            String fileContents = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

            // Create a new file in the specified directory
            String fileName = StringUtils.cleanPath(file.getName());
            File destinationDir = new File(TASK_DOWNLOAD_DIR);
            if (!destinationDir.exists()) {
                destinationDir.mkdirs(); // Create the directory if it doesn't exist
            }
            File destinationFile = new File(destinationDir, fileName);
            FileUtils.writeStringToFile(destinationFile, fileContents, StandardCharsets.UTF_8);
            String message = "Download successful. File saved to: " + destinationFile.getAbsolutePath();
            return ResponseEntity.ok(message);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to read or save the file");
        }
    }
    @Override
    public List<SubmitTaskResponseModel> getTaskSubmissionsById(Long taskId) {

        return submitTaskRepository.findByTaskId(taskId).stream().map(submitTaskEntity -> {
            TraineeEntity traineeEntity = submitTaskEntity.getTraineeEntity();
            return SubmitTaskResponseModel.builder()
                    .traineeId(traineeEntity.getId())
                    .traineeName(traineeEntity.getFullName())
                    .submissionId(submitTaskEntity.getId())
                    .date(submitTaskEntity.getDate())
                    .FileUpload(submitTaskEntity.getFileUrl())
//                    .FileName(submitTaskEntity.getFileUpload())
                    .build();
        }).toList();
   }

    @Override
    public SubmitTaskResponseModel getSubmissionsByIdUnderTask(Long taskId, Long submissionsId) {
        SubmitTaskEntity submitTaskEntity=submitTaskRepository.findByIdAndTaskId(submissionsId,taskId).orElseThrow();

        return SubmitTaskResponseModel.builder()
                .submissionId(submitTaskEntity.getId())
                .traineeId(submitTaskEntity.getTraineeEntity().getId())
                .build();
    }


}
