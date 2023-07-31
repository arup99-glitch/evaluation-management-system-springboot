package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.CreateBatchEntity;
import com.example.EvaluationManagementSystem.entity.SubmitTaskEntity;
import com.example.EvaluationManagementSystem.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CreateBatchRepository extends JpaRepository<CreateBatchEntity,Long> {

}
