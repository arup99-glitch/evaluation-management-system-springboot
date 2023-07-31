package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.CreateTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CreateTaskRepository extends JpaRepository<CreateTaskEntity,Long> {

          List<CreateTaskEntity> findById(long batchId);


}
