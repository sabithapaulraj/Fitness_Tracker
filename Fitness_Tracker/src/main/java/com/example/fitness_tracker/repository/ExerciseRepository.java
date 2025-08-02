package com.example.fitness_tracker.repository;


import com.example.fitness_tracker.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
