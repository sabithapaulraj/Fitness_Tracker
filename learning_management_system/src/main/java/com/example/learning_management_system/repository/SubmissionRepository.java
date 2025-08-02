package com.example.learning_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learning_management_system.model.Submission;

public interface SubmissionRepository extends JpaRepository<Submission,Integer>{

}