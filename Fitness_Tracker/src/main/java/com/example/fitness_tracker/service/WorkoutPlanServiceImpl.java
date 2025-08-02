package com.example.fitness_tracker.service;

import com.example.fitness_tracker.model.WorkoutPlan;
import com.example.fitness_tracker.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Override
    public WorkoutPlan createPlan(WorkoutPlan plan) {
        return workoutPlanRepository.save(plan);
    }

    @Override
    public List<WorkoutPlan> getAllPlans() {
        return workoutPlanRepository.findAll();
    }
    
    @Override
    public List<WorkoutPlan> getPlansByTrainer(Long trainerId) {
        return workoutPlanRepository.findByTrainerId(trainerId);
    }
}

