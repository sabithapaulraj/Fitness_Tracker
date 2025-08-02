package com.fitnesstracker.fitnesssessionauth.dto;

public class WorkoutPlanRequest {
    private String name;
    private String description;

    // Constructors
    public WorkoutPlanRequest() {}

    public WorkoutPlanRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
