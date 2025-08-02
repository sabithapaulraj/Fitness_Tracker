package com.fitnesstracker.fitnesssessionauth.service;

import com.fitnesstracker.fitnesssessionauth.model.User;
import com.fitnesstracker.fitnesssessionauth.model.WorkoutPlan;
import com.fitnesstracker.fitnesssessionauth.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanService {

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    public List<WorkoutPlan> findAllByTrainer(User trainer) {
        return workoutPlanRepository.findByTrainer(trainer);
    }

    public List<WorkoutPlan> findAllByTrainerId(Long trainerId) {
        return workoutPlanRepository.findByTrainerId(trainerId);
    }

    public Optional<WorkoutPlan> findById(Long id) {
        return workoutPlanRepository.findById(id);
    }

    public WorkoutPlan save(WorkoutPlan workoutPlan) {
        return workoutPlanRepository.save(workoutPlan);
    }

    public void deleteById(Long id) {
        workoutPlanRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return workoutPlanRepository.existsById(id);
    }

    public boolean isOwner(Long planId, Long trainerId) {
        Optional<WorkoutPlan> plan = findById(planId);
        return plan.isPresent() && plan.get().getTrainer().getId().equals(trainerId);
    }
}
