package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.AptitudeAndCEOInterviewEntity;
import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AptitudeAndCEOInterviewRepository extends JpaRepository<AptitudeAndCEOInterviewEntity,Long> {
    public AptitudeAndCEOInterviewEntity findByTrainee(TraineeEntity trainee);

    Optional<AptitudeAndCEOInterviewEntity> findByTraineeId(Long id);
}
