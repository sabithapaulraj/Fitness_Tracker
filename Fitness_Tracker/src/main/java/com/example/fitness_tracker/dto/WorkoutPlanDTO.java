package com.example.fitness_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutPlanDTO {
    private Long id;
    private String name;
    private String description;
    private String trainerName;
    private List<ExerciseDTO> exercises;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExerciseDTO {
        private String name;
        private int sets;
        private int reps;
        private int restTimeInSeconds;
        private int caloriesBurned;
    }
}
