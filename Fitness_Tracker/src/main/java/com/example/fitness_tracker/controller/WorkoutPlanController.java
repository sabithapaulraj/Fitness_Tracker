package com.example.fitness_tracker.controller;

import com.example.fitness_tracker.model.User;
import com.example.fitness_tracker.model.WorkoutPlan;
import com.example.fitness_tracker.repository.UserRepository;
import com.example.fitness_tracker.service.WorkoutPlanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanService workoutPlanService;
    
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> createPlan(@RequestBody WorkoutPlan plan, @RequestParam Long trainerId) {
        // Validate that the user is a trainer
        User trainer = userRepository.findById(trainerId)
                .orElse(null);
        
        if (trainer == null) {
            return ResponseEntity.badRequest().body("Trainer not found");
        }
        
        if (trainer.getRole() != User.Role.TRAINER) {
            return ResponseEntity.badRequest().body("Only trainers can create workout plans");
        }
        
        // Set the trainer for the plan
        plan.setTrainerId(trainerId);
        return ResponseEntity.ok(workoutPlanService.createPlan(plan));
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkoutPlan>> getAllPlans() {
        return ResponseEntity.ok(workoutPlanService.getAllPlans());
    }
    
    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<WorkoutPlan>> getPlansByTrainer(@PathVariable Long trainerId) {
        return ResponseEntity.ok(workoutPlanService.getPlansByTrainer(trainerId));
    }
}
