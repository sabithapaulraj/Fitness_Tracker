package com.example.learning_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learning_management_system.model.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor,Integer> {

}
