package com.example.learning_management_system.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
    private String mail;
    private String password;
    @OneToMany
    private List<Enrollment> enrollments;
    @OneToMany
    private List<Submission> submissions;
}