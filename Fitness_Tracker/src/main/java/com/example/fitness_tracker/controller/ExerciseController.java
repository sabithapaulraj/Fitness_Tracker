package com.example.fitness_tracker.controller;

import com.example.fitness_tracker.model.Exercise;
import com.example.fitness_tracker.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @PostMapping("/add")
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
        Exercise savedExercise = exerciseRepository.save(exercise);
        return ResponseEntity.ok(savedExercise);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Exercise>> getAllExercises() {
        return ResponseEntity.ok(exerciseRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @RequestBody Exercise exerciseDetails) {
        return exerciseRepository.findById(id)
                .map(exercise -> {
                    exercise.setName(exerciseDetails.getName());
                    exercise.setSets(exerciseDetails.getSets());
                    exercise.setReps(exerciseDetails.getReps());
                    exercise.setRestTimeInSeconds(exerciseDetails.getRestTimeInSeconds());
                    exercise.setCaloriesBurned(exerciseDetails.getCaloriesBurned());
                    return ResponseEntity.ok(exerciseRepository.save(exercise));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteExercise(@PathVariable Long id) {
        return exerciseRepository.findById(id)
                .map(exercise -> {
                    exerciseRepository.delete(exercise);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
