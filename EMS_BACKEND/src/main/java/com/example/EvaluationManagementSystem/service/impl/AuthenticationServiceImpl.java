package com.example.EvaluationManagementSystem.service.impl;

import com.example.EvaluationManagementSystem.entity.AdminEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import com.example.EvaluationManagementSystem.entity.TrainerEntity;
import com.example.EvaluationManagementSystem.entity.UserEntity;
import com.example.EvaluationManagementSystem.exception.CustomException.AdminNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.TraineeNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.TrainerNotFoundException;
import com.example.EvaluationManagementSystem.exception.CustomException.UserNotFoundException;
import com.example.EvaluationManagementSystem.model.*;
import com.example.EvaluationManagementSystem.repository.AdminRepository;
import com.example.EvaluationManagementSystem.repository.TraineeRepository;
import com.example.EvaluationManagementSystem.repository.TrainerRepository;
import com.example.EvaluationManagementSystem.repository.UserRepository;
import com.example.EvaluationManagementSystem.service.AuthenticationService;
import com.example.EvaluationManagementSystem.service.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private final AdminRepository adminRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final TraineeRepository traineeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;

    @Override
    public ResponseEntity<Object> createAdmin(AdminRequestModel adminRequestModel) {

        if(userRepository.findByEmail(adminRequestModel.getEmail()).isPresent()){
            throw new RuntimeException("UserNotFoundException email already Exists");
        }

        UserEntity user= UserEntity.builder()
                .id(adminRequestModel.getId())
                .email(adminRequestModel.getEmail())
                .password(passwordEncoder.encode(adminRequestModel.getPassword()))
                .role("Admin")
                .build();
        user = userRepository.save(user);

        AdminEntity admin = AdminEntity.builder()
                .fullName(adminRequestModel.getFullName())
                .gender(adminRequestModel.getGender())
                .email(adminRequestModel.getEmail())
                .password(passwordEncoder.encode(adminRequestModel.getPassword()))
                .contactNumber(adminRequestModel.getContactNumber())
                .user(user)
                .build();


         AdminEntity savedadmin= adminRepository.save(admin);
        var jwtToken = jwtService.generateToken(user);
        return new ResponseEntity<>(savedadmin , HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Object> createTrainee(TraineeRequestModel traineeRequestModel) {
        if(userRepository.findByEmail(traineeRequestModel.getEmail()).isPresent()){
            throw new RuntimeException("UserNotFoundException email already Exists");
        }
        UserEntity user = UserEntity.builder()
                .email(traineeRequestModel.getEmail())
                .password(passwordEncoder.encode(traineeRequestModel.getPassword()))
                .role("Trainee")
                .build();


        user = userRepository.save(user);

        TraineeEntity trainee = TraineeEntity.builder()
                .fullName(traineeRequestModel.getFullName())
                .gender(traineeRequestModel.getGender())
                .dateOfBirth(traineeRequestModel.getDateOfBirth())
                .email(traineeRequestModel.getEmail())
                .password(passwordEncoder.encode(traineeRequestModel.getPassword()))
                .contactNumber(traineeRequestModel.getContactNumber())
                .degreeName(traineeRequestModel.getDegreeName())
                .educationalInstitute(traineeRequestModel.getEducationalInstitute())
                .cgpa(traineeRequestModel.getCgpa())
                .passingYear(traineeRequestModel.getPassingYear())
                .presentAddress(traineeRequestModel.getPresentAddress())
                .user(user)
                .build();


        TraineeEntity savedTrainee= traineeRepository.save(trainee);
        var jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(savedTrainee , HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<Object> createTrainer(TrainerRequestModel trainerRequestModel) {
        if(userRepository.findByEmail(trainerRequestModel.getEmail()).isPresent()){
            throw new RuntimeException("UserNotFoundException email already Exists");
        }
        UserEntity user = UserEntity.builder()
                .email(trainerRequestModel.getEmail())
                .password(passwordEncoder.encode(trainerRequestModel.getPassword()))
                .role("Trainer")
                .build();
        user = userRepository.save(user);
        TrainerEntity trainer = TrainerEntity.builder()
                .fullName(trainerRequestModel.getFullName())
                .email(trainerRequestModel.getEmail())
                .password(passwordEncoder.encode(trainerRequestModel.getPassword()))
                .designation(trainerRequestModel.getDesignation())
                .joiningDate(trainerRequestModel.getJoiningDate())
                .yearsOfExperience(trainerRequestModel.getYearsOfExperience())
                .expertises(trainerRequestModel.getExpertises())
                .contactNumber(trainerRequestModel.getContactNumber())
                .presentAddress(trainerRequestModel.getPresentAddress())
                .user(user)
                .build();
        TrainerEntity savedTrainer= trainerRepository.save(trainer);
        var jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(savedTrainer , HttpStatus.CREATED);

    }
    public AuthenticationResponseModel authenticate(LoginRequestModel request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        Optional<UserEntity> userEntity = repository.findByEmail(request.getEmail());
        if(userEntity.isPresent()){
            UserEntity user = userEntity.get();
            var jwtToken = jwtService.generateToken(user);
            if(user.getRole().equalsIgnoreCase("Trainee")) {
                TraineeEntity trainee = traineeRepository.findByUser(user);
                return AuthenticationResponseModel.builder()
                        .token(jwtToken)
                        .user(user)
                        .trainee(trainee)
                        .build();
            }
            else if (user.getRole().equalsIgnoreCase("Trainer")) {
                TrainerEntity trainer = trainerRepository.findByUser(user);
                return AuthenticationResponseModel.builder()
                        .token(jwtToken)
                        .user(user)
                        .trainer(trainer)
                        .build();

            }
            else {
                AdminEntity admin = adminRepository.findByUser(user);
                return AuthenticationResponseModel.builder()
                        .token(jwtToken)
                        .user(user)
                        .admin(admin)
                        .build();
            }


        }

         else{
            throw new RuntimeException("user not found");
        }

    }
    @Override
    public ResponseEntity<List<UserEntity>> viewAllUser() {
        List<UserEntity> users = userRepository.findAll();
        return new ResponseEntity<>(users , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TraineeEntity>> viewAllTrainee() {
        List<TraineeEntity> trainees = traineeRepository.findAll();
        return new ResponseEntity<>(trainees , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TrainerEntity>> viewAllTrainer() {
        List<TrainerEntity> trainers = trainerRepository.findAll();
        return new ResponseEntity<>(trainers , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AdminEntity>> viewAllAdmin() {
        List<AdminEntity> admin = adminRepository.findAll();
        return new ResponseEntity<>(admin , HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserEntity> findUserById(Long userId) {
        if( userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException("User Not Found");
        }
        else{
            return new ResponseEntity<>( userRepository.findById(userId).get() , HttpStatus.OK );
        }
    }

    @Override
    public ResponseEntity<TraineeEntity> findTraineeById(Long traineeId) {
        if( traineeRepository.findById(traineeId).isEmpty()){
            throw new TraineeNotFoundException("Trainee Not Found");
        }
        else{
            return new ResponseEntity<>( traineeRepository.findById(traineeId).get() , HttpStatus.OK );
        }
    }

    @Override
    public ResponseEntity<TrainerEntity> findTrainerById(Long trainerId) {
        if( trainerRepository.findById(trainerId).isEmpty()){
            throw new TrainerNotFoundException("trainer not found.");
        }
        else{
            return new ResponseEntity<>( trainerRepository.findById(trainerId).get() , HttpStatus.OK );
        }
    }

    @Override
    public ResponseEntity<AdminEntity> findAdminById(Long adminId) {
        if( adminRepository.findById(adminId).isEmpty()){
            throw new AdminNotFoundException("Admin not found.");
        }
        else{
            return new ResponseEntity<>( adminRepository.findById(adminId).get() , HttpStatus.OK );
        }
    }

    @Override
    public ResponseEntity<Object> findAllTraineeFullName() {
        List<TraineeEntity> traineeEntities = traineeRepository.findAll();
        List<Map<String, Object>> trainees = new ArrayList<>();
        for (TraineeEntity trainee : traineeEntities) {
            Map<String, Object>  traineeData = new HashMap<>();
            traineeData.put("id", trainee.getId());
            traineeData.put("name", trainee.getFullName());
            trainees.add(traineeData);
        }
        // Create the final response
        Map<String, Object> response = new HashMap<>();
        response.put("Total Trainees", trainees.size());
        response.put("Trainees", trainees);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void deleteTrainee(Long traineeId) {
        Optional<TraineeEntity> traineeEntity = traineeRepository.findById(traineeId);
        if(traineeEntity.isPresent()){
            traineeRepository.delete(traineeEntity.orElseThrow());
        }
        else {
            throw new TrainerNotFoundException("Trainee Id is not found.");
        }

    }

    @Override
    public void deleteTrainer(Long trainerId) {
        Optional<TrainerEntity> trainerEntity = trainerRepository.findById(trainerId);
        if(trainerEntity.isPresent()){
            trainerRepository.delete(trainerEntity.orElseThrow());
        }
        else {
            throw new TrainerNotFoundException("Trainer Id is not found.");
        }
    }

    @Override
    public void deleteAdmin(Long adminId) {
        Optional<AdminEntity> adminEntity = adminRepository.findById(adminId);
        if(adminEntity.isPresent()){
            adminRepository.delete(adminEntity.orElseThrow());
        }
        else {
            throw new AdminNotFoundException("Admin Id is not found.");
        }

    }


    @Override
    public ResponseEntity<Object> updateTraineeById(Long traineeId, TraineeRequestModel requestModel) {
        Optional<TraineeEntity> traineeOptional = traineeRepository.findById(traineeId);

        if (traineeOptional.isPresent()) {
            TraineeEntity trainee = traineeOptional.get();
            trainee.setEmail(requestModel.getEmail());
            trainee.setPassword(requestModel.getPassword());
            trainee.setFullName(requestModel.getFullName());
            trainee.setDegreeName(requestModel.getDegreeName());
            trainee.setEducationalInstitute(requestModel.getEducationalInstitute());
            trainee.setCgpa(requestModel.getCgpa());
            trainee.setPassingYear(requestModel.getPassingYear());
            trainee.setPresentAddress(requestModel.getPresentAddress());
            trainee.setContactNumber(requestModel.getContactNumber());
            trainee.setDateOfBirth(requestModel.getDateOfBirth());
            trainee.setGender(requestModel.getGender());


            TraineeEntity updatedTrainee = traineeRepository.save(trainee);
            return ResponseEntity.ok(updatedTrainee);
        } else {
            throw  new TraineeNotFoundException("Trainer is not found with this id");
        }
    }

    @Override
    public ResponseEntity<Object> updateTrainerById(Long trainerId, TrainerRequestModel requestModel) {
        Optional<TrainerEntity> trainerOptional = trainerRepository.findById(trainerId);

        if (trainerOptional.isPresent()) {
            TrainerEntity trainer = trainerOptional.get();
            trainer.setFullName(requestModel.getFullName());
            trainer.setEmail(requestModel.getEmail());
            trainer.setPassword(requestModel.getPassword());
            trainer.setDesignation(requestModel.getDesignation());
            trainer.setJoiningDate(requestModel.getJoiningDate());
            trainer.setYearsOfExperience(requestModel.getYearsOfExperience());
            trainer.setExpertises(requestModel.getExpertises());
            trainer.setContactNumber(requestModel.getContactNumber());
            trainer.setPresentAddress(requestModel.getPresentAddress());

            TrainerEntity updatedTrainer = trainerRepository.save(trainer);
            return ResponseEntity.ok(updatedTrainer);

        } else {
            throw new TrainerNotFoundException("Trainer Not Found ");
        }
    }

}
