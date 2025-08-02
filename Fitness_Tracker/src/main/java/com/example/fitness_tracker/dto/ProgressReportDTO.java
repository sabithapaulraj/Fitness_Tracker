package com.example.fitness_tracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressReportDTO {
    private Long userId;
    private String username;
    private String weekStart;
    private int caloriesBurned;
    private int exercisesCompleted;
    private int improvementPercentage; // Compared to last week
    private String summary;
}
