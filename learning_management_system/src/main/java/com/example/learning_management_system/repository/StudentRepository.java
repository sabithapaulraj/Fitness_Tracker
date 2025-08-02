package com.example.learning_management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.learning_management_system.model.Student;

public interface StudentRepository extends JpaRepository<Student,Integer>{

}