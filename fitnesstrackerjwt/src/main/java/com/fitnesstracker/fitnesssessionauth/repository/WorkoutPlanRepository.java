package com.fitnesstracker.fitnesssessionauth.repository;

import com.fitnesstracker.fitnesssessionauth.model.WorkoutPlan;
import com.fitnesstracker.fitnesssessionauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
    List<WorkoutPlan> findByTrainer(User trainer);
    List<WorkoutPlan> findByTrainerId(Long trainerId);
}
