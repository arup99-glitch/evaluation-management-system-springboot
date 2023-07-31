package com.example.EvaluationManagementSystem.controller;


import com.example.EvaluationManagementSystem.entity.AdminEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.entity.TrainerEntity;
import com.example.EvaluationManagementSystem.entity.UserEntity;
import com.example.EvaluationManagementSystem.model.*;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.service.AuthenticationService;
import com.example.EvaluationManagementSystem.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final AuthenticationServiceImpl service;
    private final TraineeRepository traineeRepository;

    @PostMapping("/register/admin")
    public ResponseEntity<Object> createAdmin(@RequestBody AdminRequestModel adminModel) {
        return authenticationService.createAdmin(adminModel);

    }
    @PostMapping("/register/trainee")
    public ResponseEntity<Object> createTrainee(@RequestBody TraineeRequestModel traineeRequestModel) {
       return authenticationService.createTrainee(traineeRequestModel);

    }
    @PostMapping("/register/trainer")
    public ResponseEntity<Object> createTrainer(@RequestBody TrainerRequestModel trainerRequestModel) {
      return  authenticationService.createTrainer(trainerRequestModel);

    }
    @PostMapping("/user/login")
    public ResponseEntity<Object> authenticate(@RequestBody LoginRequestModel request){
        return ResponseEntity.ok(service.authenticate(request));

    }
    @GetMapping("/viewUsers")
    ResponseEntity<List<UserEntity>> viewUsers(){
        return authenticationService.viewAllUser();
    }

    @GetMapping("/viewAdmin")
    ResponseEntity<List<AdminEntity>> viewAllAdmin(){
        return authenticationService.viewAllAdmin();
    }
    @GetMapping("/user/{userId}")
    ResponseEntity<UserEntity> findUserById(@PathVariable Long userId){
        return authenticationService.findUserById(userId);
    }
    @GetMapping("/admin/{adminId}")
    ResponseEntity<AdminEntity> findAdminById(@PathVariable Long adminId){
        return authenticationService.findAdminById(adminId);
    }
    @DeleteMapping("/admin/delete/{adminId}")
    public void deleteAdmin(@PathVariable Long adminId){
        authenticationService.deleteAdmin(adminId);
    }


}
