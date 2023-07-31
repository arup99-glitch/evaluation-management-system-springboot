package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.ManagerEvaluationEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ManagerEvaluationRepository extends JpaRepository<ManagerEvaluationEntity,Long> {
    public ManagerEvaluationEntity findByTrainee(TraineeEntity trainee);
    Optional<ManagerEvaluationEntity> findByTraineeId(Long id);
}
