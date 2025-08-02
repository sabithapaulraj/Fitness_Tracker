package com.example.fitness_tracker.repository;


import com.example.fitness_tracker.model.ProgressReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressReportRepository extends JpaRepository<ProgressReport, Long> {
    List<ProgressReport> findByUserId(Long userId);
}
