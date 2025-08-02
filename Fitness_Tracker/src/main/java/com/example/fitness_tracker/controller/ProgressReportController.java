package com.example.fitness_tracker.controller;

import com.example.fitness_tracker.dto.ProgressReportDTO;
import com.example.fitness_tracker.service.ProgressReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ProgressReportController {

    @Autowired
    private ProgressReportService reportService;

    @PostMapping("/generate/{userId}")
    public ResponseEntity<ProgressReportDTO> generateWeeklyReport(@PathVariable Long userId) {
        try {
            ProgressReportDTO report = reportService.generateWeeklyReport(userId);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProgressReportDTO>> getUserReports(@PathVariable Long userId) {
        List<ProgressReportDTO> reports = reportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }
}
