package com.example.learning_management_system.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Assignment {
    @Id
    private Integer id;
    private Integer course_id;
    private String title;
    private LocalDateTime due_date;
}
