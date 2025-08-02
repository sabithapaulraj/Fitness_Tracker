package com.fitnesstracker.fitnesssessionauth.repository;

import com.fitnesstracker.fitnesssessionauth.model.Exercise;
import com.fitnesstracker.fitnesssessionauth.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByWorkoutPlan(WorkoutPlan workoutPlan);
    List<Exercise> findByWorkoutPlanId(Long workoutPlanId);
}
