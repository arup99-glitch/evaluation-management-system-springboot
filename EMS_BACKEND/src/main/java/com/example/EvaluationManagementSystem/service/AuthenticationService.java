package com.example.EvaluationManagementSystem.service;

import com.example.EvaluationManagementSystem.entity.AdminEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.entity.TrainerEntity;
import com.example.EvaluationManagementSystem.entity.UserEntity;
import com.example.EvaluationManagementSystem.model.AdminRequestModel;
import com.example.EvaluationManagementSystem.model.TraineeRequestModel;
import com.example.EvaluationManagementSystem.model.TrainerRequestModel;
import com.example.EvaluationManagementSystem.repository.AdminRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AuthenticationService {
    ResponseEntity<Object> createAdmin(AdminRequestModel adminRequestModel);
    ResponseEntity<Object> createTrainee(TraineeRequestModel traineeRequestModel);
    ResponseEntity<Object>createTrainer(TrainerRequestModel trainerRequestModel);
    ResponseEntity<List<UserEntity>> viewAllUser();
    ResponseEntity<List<TraineeEntity>> viewAllTrainee();
    ResponseEntity<List<TrainerEntity>> viewAllTrainer();
    ResponseEntity<List<AdminEntity>> viewAllAdmin();
    ResponseEntity<UserEntity> findUserById(Long userId);
    ResponseEntity<TraineeEntity> findTraineeById(Long traineeId);
    ResponseEntity<TrainerEntity> findTrainerById(Long trainerId);
    ResponseEntity<AdminEntity> findAdminById(Long adminId);
    ResponseEntity<Object>findAllTraineeFullName();
    void deleteTrainee(Long traineeId);
    void deleteTrainer (Long trainerId);
    void deleteAdmin(Long adminId);
    ResponseEntity<Object> updateTraineeById(Long traineeId,TraineeRequestModel requestModel);
    ResponseEntity<Object> updateTrainerById(Long trainerId, TrainerRequestModel requestModel);



}
