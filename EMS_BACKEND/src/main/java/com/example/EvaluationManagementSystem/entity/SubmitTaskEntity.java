package com.example.EvaluationManagementSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Entity
@Table(name="submit_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmitTaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    private CreateTaskEntity task;
    private Date date;
    private String fileUrl;
    @ManyToOne(cascade = CascadeType.ALL)
    private TraineeEntity traineeEntity;
}
