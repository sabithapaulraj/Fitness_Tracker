package com.example.learning_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
@Entity
public class Courses {
    @Id
    private Integer id;
    private String title;
    private Integer course_code;
    private Integer credits;
}
