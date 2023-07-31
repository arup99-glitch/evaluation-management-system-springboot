package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.SubmitTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubmitTaskRepository extends JpaRepository<SubmitTaskEntity,Long> {
    List<SubmitTaskEntity> findByTaskId(Long taskId);

    Optional<SubmitTaskEntity> findByIdAndTaskId(Long submissionsId, Long taskId);
    Optional<SubmitTaskEntity> findByTraineeEntityIdAndTaskId(Long TraineeId,Long taskId);
}
