package com.example.learning_management_system.repository;

import com.example.learning_management_system.model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoursesRepository extends JpaRepository<Courses,Integer>{

}