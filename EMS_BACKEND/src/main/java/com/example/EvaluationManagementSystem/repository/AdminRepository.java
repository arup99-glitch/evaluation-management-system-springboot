package com.example.EvaluationManagementSystem.repository;

import com.example.EvaluationManagementSystem.entity.AdminEntity;
import com.example.EvaluationManagementSystem.entity.TrainerEntity;
import com.example.EvaluationManagementSystem.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<AdminEntity,Long> {
    AdminEntity findByUser(UserEntity userId);

}
