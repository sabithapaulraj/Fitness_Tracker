package com.example.fitness_tracker.repository;

import com.example.fitness_tracker.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByTrainerId(Long trainerId);
}
