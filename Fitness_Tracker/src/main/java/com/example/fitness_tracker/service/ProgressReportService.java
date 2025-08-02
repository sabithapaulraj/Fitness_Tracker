package com.example.fitness_tracker.service;

import com.example.fitness_tracker.dto.ProgressReportDTO;
import com.example.fitness_tracker.model.ProgressReport;

import java.util.List;

public interface ProgressReportService {
    ProgressReportDTO generateWeeklyReport(Long userId);
    List<ProgressReportDTO> getReportsByUserId(Long userId);
    ProgressReport saveReport(ProgressReport report);
}
