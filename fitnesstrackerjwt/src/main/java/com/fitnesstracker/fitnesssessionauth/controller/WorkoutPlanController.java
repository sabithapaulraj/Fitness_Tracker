package com.fitnesstracker.fitnesssessionauth.controller;

import com.fitnesstracker.fitnesssessionauth.dto.WorkoutPlanRequest;
import com.fitnesstracker.fitnesssessionauth.dto.ExerciseRequest;
import com.fitnesstracker.fitnesssessionauth.model.User;
import com.fitnesstracker.fitnesssessionauth.model.WorkoutPlan;
import com.fitnesstracker.fitnesssessionauth.model.Exercise;
import com.fitnesstracker.fitnesssessionauth.service.CustomUserDetailsService;
import com.fitnesstracker.fitnesssessionauth.service.WorkoutPlanService;
import com.fitnesstracker.fitnesssessionauth.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans")
@PreAuthorize("hasRole('TRAINER')")
public class WorkoutPlanController {

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Create a new workout plan
    @PostMapping
    public ResponseEntity<?> createWorkoutPlan(@RequestBody WorkoutPlanRequest request, 
                                             @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
            
            WorkoutPlan workoutPlan = new WorkoutPlan();
            workoutPlan.setName(request.getName());
            workoutPlan.setDescription(request.getDescription());
            workoutPlan.setTrainer(trainer);
            
            WorkoutPlan savedPlan = workoutPlanService.save(workoutPlan);
            return ResponseEntity.ok(savedPlan);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to create workout plan: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // Get all workout plans for the authenticated trainer
    @GetMapping
    public ResponseEntity<List<WorkoutPlan>> getAllWorkoutPlans(@AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        List<WorkoutPlan> plans = workoutPlanService.findAllByTrainer(trainer);
        return ResponseEntity.ok(plans);
    }

    // Get a specific workout plan by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkoutPlan(@PathVariable Long id, 
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        if (!workoutPlanService.isOwner(id, trainer.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Access denied");
            return ResponseEntity.status(403).body(response);
        }

        Optional<WorkoutPlan> plan = workoutPlanService.findById(id);
        if (plan.isPresent()) {
            return ResponseEntity.ok(plan.get());
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Workout plan not found");
            return ResponseEntity.notFound().build();
        }
    }

    // Update a workout plan
    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkoutPlan(@PathVariable Long id, 
                                             @RequestBody WorkoutPlanRequest request,
                                             @AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        if (!workoutPlanService.isOwner(id, trainer.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Access denied");
            return ResponseEntity.status(403).body(response);
        }

        Optional<WorkoutPlan> optionalPlan = workoutPlanService.findById(id);
        if (optionalPlan.isPresent()) {
            WorkoutPlan plan = optionalPlan.get();
            plan.setName(request.getName());
            plan.setDescription(request.getDescription());
            
            WorkoutPlan updatedPlan = workoutPlanService.save(plan);
            return ResponseEntity.ok(updatedPlan);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Workout plan not found");
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a workout plan
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkoutPlan(@PathVariable Long id, 
                                             @AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        if (!workoutPlanService.isOwner(id, trainer.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Access denied");
            return ResponseEntity.status(403).body(response);
        }

        if (workoutPlanService.existsById(id)) {
            workoutPlanService.deleteById(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Workout plan deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Workout plan not found");
            return ResponseEntity.notFound().build();
        }
    }

    // Add exercise to a workout plan
    @PostMapping("/{id}/exercises")
    public ResponseEntity<?> addExercise(@PathVariable Long id, 
                                       @RequestBody ExerciseRequest request,
                                       @AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        if (!workoutPlanService.isOwner(id, trainer.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Access denied");
            return ResponseEntity.status(403).body(response);
        }

        Optional<WorkoutPlan> optionalPlan = workoutPlanService.findById(id);
        if (optionalPlan.isPresent()) {
            WorkoutPlan plan = optionalPlan.get();
            
            Exercise exercise = new Exercise();
            exercise.setName(request.getName());
            exercise.setDescription(request.getDescription());
            exercise.setSets(request.getSets());
            exercise.setReps(request.getReps());
            exercise.setRestTimeSeconds(request.getRestTimeSeconds());
            exercise.setInstructions(request.getInstructions());
            exercise.setWorkoutPlan(plan);
            
            Exercise savedExercise = exerciseService.save(exercise);
            return ResponseEntity.ok(savedExercise);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Workout plan not found");
            return ResponseEntity.notFound().build();
        }
    }

    // Get exercises for a workout plan
    @GetMapping("/{id}/exercises")
    public ResponseEntity<?> getExercises(@PathVariable Long id, 
                                        @AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        if (!workoutPlanService.isOwner(id, trainer.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Access denied");
            return ResponseEntity.status(403).body(response);
        }

        List<Exercise> exercises = exerciseService.findByWorkoutPlanId(id);
        return ResponseEntity.ok(exercises);
    }

    // Update an exercise
    @PutMapping("/{planId}/exercises/{exerciseId}")
    public ResponseEntity<?> updateExercise(@PathVariable Long planId, 
                                          @PathVariable Long exerciseId,
                                          @RequestBody ExerciseRequest request,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        if (!workoutPlanService.isOwner(planId, trainer.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Access denied");
            return ResponseEntity.status(403).body(response);
        }

        if (!exerciseService.belongsToWorkoutPlan(exerciseId, planId)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Exercise does not belong to this workout plan");
            return ResponseEntity.status(400).body(response);
        }

        Optional<Exercise> optionalExercise = exerciseService.findById(exerciseId);
        if (optionalExercise.isPresent()) {
            Exercise exercise = optionalExercise.get();
            exercise.setName(request.getName());
            exercise.setDescription(request.getDescription());
            exercise.setSets(request.getSets());
            exercise.setReps(request.getReps());
            exercise.setRestTimeSeconds(request.getRestTimeSeconds());
            exercise.setInstructions(request.getInstructions());
            
            Exercise updatedExercise = exerciseService.save(exercise);
            return ResponseEntity.ok(updatedExercise);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Exercise not found");
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an exercise
    @DeleteMapping("/{planId}/exercises/{exerciseId}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long planId, 
                                          @PathVariable Long exerciseId,
                                          @AuthenticationPrincipal UserDetails userDetails) {
        User trainer = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        if (!workoutPlanService.isOwner(planId, trainer.getId())) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Access denied");
            return ResponseEntity.status(403).body(response);
        }

        if (!exerciseService.belongsToWorkoutPlan(exerciseId, planId)) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Exercise does not belong to this workout plan");
            return ResponseEntity.status(400).body(response);
        }

        if (exerciseService.existsById(exerciseId)) {
            exerciseService.deleteById(exerciseId);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Exercise deleted successfully");
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Exercise not found");
            return ResponseEntity.notFound().build();
        }
    }
}
