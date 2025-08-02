package com.example.learning_management_system.repository;

import com.example.learning_management_system.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssignmentRepository extends JpaRepository<Assignment,Integer>{

}