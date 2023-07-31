package com.example.EvaluationManagementSystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="create_batch")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String batchName;
    private String description;
    private Date startingDate;
    private Date endingDate;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<TraineeEntity> trainees = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CreateTaskEntity> createTaskEntities;

}
