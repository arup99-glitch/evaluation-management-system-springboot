package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.TaskEvaluationEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskEvaluationRepository extends JpaRepository<TaskEvaluationEntity,Long> {
    public List<TaskEvaluationEntity> findByTrainee(TraineeEntity trainee);
    Optional<TaskEvaluationEntity> findByCreateDailyTaskIdAndTraineeId(Long taskId,Long TraineeId);
}
