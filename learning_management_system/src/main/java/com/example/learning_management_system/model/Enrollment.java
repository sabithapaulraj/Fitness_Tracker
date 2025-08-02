package com.example.learning_management_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
@Entity
public class Enrollment {
    @Id
    private Integer id;
    @ManyToOne
    private Student stu_id;
    private String status;
    private LocalDateTime enrolled_at;

}