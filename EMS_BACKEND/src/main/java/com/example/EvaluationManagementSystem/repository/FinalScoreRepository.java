package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.FinalScoreEntity;
import com.example.EvaluationManagementSystem.entity.ManagerEvaluationEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinalScoreRepository extends JpaRepository<FinalScoreEntity,Long> {
    public FinalScoreEntity findByTraineeEntity(TraineeEntity trainee);
}
