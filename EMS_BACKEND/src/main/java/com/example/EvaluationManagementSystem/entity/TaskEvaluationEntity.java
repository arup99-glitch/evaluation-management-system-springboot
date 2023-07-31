package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="task_evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskEvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String taskType;
    private Double requirementUnderstanding;
    private Double expectedOutput;
    private Double codeQuality;
    private Double demonstration;
    private Double codeUnderstanding;
    private Double totalMarks;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trainee_id")
    public TraineeEntity trainee;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    public CreateTaskEntity createDailyTask;

}
