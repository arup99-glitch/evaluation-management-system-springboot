package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="daily_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taskName;
    private Date startingDate;
    private Date deadline;
    private String taskType;
    private Long batchId;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    public List<SubmitTaskEntity> submissions;

}
