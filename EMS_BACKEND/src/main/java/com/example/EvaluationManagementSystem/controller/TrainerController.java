package com.example.EvaluationManagementSystem.controller;

import com.example.EvaluationManagementSystem.entity.TrainerEntity;
import com.example.EvaluationManagementSystem.model.TraineeRequestModel;
import com.example.EvaluationManagementSystem.model.TrainerRequestModel;
import com.example.EvaluationManagementSystem.model.TrainerResponseModel;
import com.example.EvaluationManagementSystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class TrainerController {
    @Autowired
    private final AuthenticationService authenticationService;
    @GetMapping("/viewTrainers")
    ResponseEntity<List<TrainerEntity>> viewTrainers(){
        return authenticationService.viewAllTrainer();
    }
    @GetMapping("/trainer/{trainerId}")
    ResponseEntity<TrainerEntity> findTrainerById(@PathVariable Long trainerId){
        return authenticationService.findTrainerById(trainerId);
    }
    @DeleteMapping("/trainer/delete/{trainerId}")
    public void deleteTrainer(@PathVariable Long trainerId){
        authenticationService.deleteTrainer(trainerId);
    }
    @PutMapping("update/trainer/{trainerId}")
    public ResponseEntity<Object> updateTrainer(@PathVariable Long trainerId, @RequestBody TrainerRequestModel requestModel) {
        return authenticationService.updateTrainerById(trainerId, requestModel);
    }
}
