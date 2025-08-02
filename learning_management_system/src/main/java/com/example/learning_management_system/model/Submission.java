package com.example.learning_management_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Submission {
    @Id
    private Integer id;
    @ManyToOne
    private Student student;
    private Double grade;
    private LocalDateTime submitted_at;
}