package com.example.fitness_tracker.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
