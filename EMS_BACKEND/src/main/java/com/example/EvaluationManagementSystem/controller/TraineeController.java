package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.model.TraineeRequestModel;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.AuthenticationService;
import com.example.EvaluationManagementSystem.service.CreateBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TraineeController {
    @Autowired
    private final CreateBatchService batchService;
    private final AuthenticationService authenticationService;
    private final TraineeRepository traineeRepository;

    @GetMapping("/trainees/batch/{batchId}")
    ResponseEntity<Set<TraineeEntity>> traineeUnderBatchId(@PathVariable Long batchId) {
        return batchService.findTraineeUnderBatchById(batchId);
    }
    @GetMapping("/trainee/{traineeId}")
    ResponseEntity<TraineeEntity> findTraineeById(@PathVariable Long traineeId){
        return authenticationService.findTraineeById(traineeId);
    }
    @GetMapping("/viewTrainees")
    ResponseEntity<List<TraineeEntity>> viewTrainees(){
        return authenticationService.viewAllTrainee();
    }
    @GetMapping("/allTraineeName")
    public ResponseEntity<Object> findAllTraineeFullName() {
        List<TraineeEntity> traineeEntities = traineeRepository.findAll();
        List<Map<String, Object>> trainees = new ArrayList<>();

        for (TraineeEntity trainee : traineeEntities) {
            Map<String, Object> traineeData = new HashMap<>();
            traineeData.put("id", trainee.getId());
            traineeData.put("name", trainee.getFullName());
            trainees.add(traineeData);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("TotalTrainees", trainees.size());
        response.put("Trainees", trainees);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/trainee/delete/{traineeId}")
    void deleteTrainee(@PathVariable Long traineeId){
        authenticationService.deleteTrainee(traineeId);
    }
    @PutMapping("/update/{traineeId}")
    public ResponseEntity<Object> updateTrainee(@PathVariable Long traineeId, @RequestBody TraineeRequestModel requestModel) {
        return authenticationService.updateTraineeById(traineeId, requestModel);
    }


}