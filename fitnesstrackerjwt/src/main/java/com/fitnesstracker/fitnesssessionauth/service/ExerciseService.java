package com.fitnesstracker.fitnesssessionauth.service;

import com.fitnesstracker.fitnesssessionauth.model.Exercise;
import com.fitnesstracker.fitnesssessionauth.model.WorkoutPlan;
import com.fitnesstracker.fitnesssessionauth.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExerciseService {

    @Autowired
    private ExerciseRepository exerciseRepository;

    public List<Exercise> findByWorkoutPlan(WorkoutPlan workoutPlan) {
        return exerciseRepository.findByWorkoutPlan(workoutPlan);
    }

    public List<Exercise> findByWorkoutPlanId(Long workoutPlanId) {
        return exerciseRepository.findByWorkoutPlanId(workoutPlanId);
    }

    public Optional<Exercise> findById(Long id) {
        return exerciseRepository.findById(id);
    }

    public Exercise save(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    public void deleteById(Long id) {
        exerciseRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return exerciseRepository.existsById(id);
    }

    public boolean belongsToWorkoutPlan(Long exerciseId, Long workoutPlanId) {
        Optional<Exercise> exercise = findById(exerciseId);
        return exercise.isPresent() && exercise.get().getWorkoutPlan().getId().equals(workoutPlanId);
    }
}
