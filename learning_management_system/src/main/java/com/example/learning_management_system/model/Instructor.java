package com.example.learning_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Instructor {
    @Id
    private Integer id;
    private String name;
    private String email;
    private String expertise;
}