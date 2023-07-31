package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="final_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double dailyMarks;
    private Double miniProject;
    private Double midProject;
    private Double finalProject;
    private Double managerEvaluation;
    private Double ceoHrInterview;
    private Double totalMarks;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="trainee_id")
    public TraineeEntity traineeEntity;
}
