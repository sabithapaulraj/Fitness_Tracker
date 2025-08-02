package com.example.fitness_tracker.service;

import com.example.fitness_tracker.dto.ProgressReportDTO;
import com.example.fitness_tracker.model.Exercise;
import com.example.fitness_tracker.model.ProgressReport;
import com.example.fitness_tracker.model.User;
import com.example.fitness_tracker.repository.ExerciseRepository;
import com.example.fitness_tracker.repository.ProgressReportRepository;
import com.example.fitness_tracker.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgressReportServiceImpl implements ProgressReportService {

    @Autowired
    private ProgressReportRepository reportRepo;

    @Autowired
    private ExerciseRepository exerciseRepo;

    @Autowired
    private UserRepository userRepo;

    @Override
    public ProgressReportDTO generateWeeklyReport(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Get this week's data
        List<Exercise> exercises = exerciseRepo.findAll();
        int totalCalories = exercises.stream().mapToInt(Exercise::getCaloriesBurned).sum();
        int exercisesCompleted = exercises.size();

        // Get last week's data for comparison
        List<ProgressReport> lastWeekReports = reportRepo.findByUserId(userId);
        int improvementPercentage = calculateImprovement(exercisesCompleted, lastWeekReports);

        // Save current week's report
        ProgressReport currentReport = ProgressReport.builder()
                .user(user)
                .weekStart(LocalDate.now().minusDays(LocalDate.now().getDayOfWeek().getValue() - 1))
                .caloriesBurned(totalCalories)
                .exercisesCompleted(exercisesCompleted)
                .build();
        reportRepo.save(currentReport);

        // Return DTO
        return ProgressReportDTO.builder()
                .userId(userId)
                .username(user.getUsername())
                .weekStart(LocalDate.now().minusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE))
                .caloriesBurned(totalCalories)
                .exercisesCompleted(exercisesCompleted)
                .improvementPercentage(improvementPercentage)
                .summary(String.format("This week: %d exercises, %d calories. Improvement: %d%%", 
                        exercisesCompleted, totalCalories, improvementPercentage))
                .build();
    }

    @Override
    public List<ProgressReportDTO> getReportsByUserId(Long userId) {
        User user = userRepo.findById(userId).orElse(null);
        return reportRepo.findByUserId(userId).stream()
                .map(report -> ProgressReportDTO.builder()
                        .userId(userId)
                        .username(user != null ? user.getUsername() : "Unknown")
                        .weekStart(report.getWeekStart() != null ? 
                                  report.getWeekStart().format(DateTimeFormatter.ISO_LOCAL_DATE) : "N/A")
                        .caloriesBurned(report.getCaloriesBurned())
                        .exercisesCompleted(report.getExercisesCompleted())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProgressReport saveReport(ProgressReport report) {
        return reportRepo.save(report);
    }

    private int calculateImprovement(int currentExercises, List<ProgressReport> pastReports) {
        if (pastReports.isEmpty()) return 0;
        
        int lastWeekExercises = pastReports.get(pastReports.size() - 1).getExercisesCompleted();
        if (lastWeekExercises == 0) return currentExercises > 0 ? 100 : 0;
        
        return ((currentExercises - lastWeekExercises) * 100) / lastWeekExercises;
    }
}
