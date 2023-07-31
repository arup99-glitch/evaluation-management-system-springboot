package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="manager_evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManagerEvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double commonScore;
    private Double officeRules;
    private Double sincerity;
    private Double qualityMindset;
    private Double attendance;
    private Double communicationSkill;
    private Double englishLanguageSkill;
    private Double totalMarks;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="trainee_id")
    public TraineeEntity trainee;

}