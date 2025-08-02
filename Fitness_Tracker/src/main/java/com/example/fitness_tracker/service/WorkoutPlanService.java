package com.example.fitness_tracker.service;

import com.example.fitness_tracker.model.WorkoutPlan;

import java.util.List;

public interface WorkoutPlanService {
    WorkoutPlan createPlan(WorkoutPlan plan);
    List<WorkoutPlan> getAllPlans();
    List<WorkoutPlan> getPlansByTrainer(Long trainerId);
}

