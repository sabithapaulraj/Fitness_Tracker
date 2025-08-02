package com.fitnesstracker.fitnesssessionauth.dto;

public class ExerciseRequest {
    private String name;
    private String description;
    private Integer sets;
    private Integer reps;
    private Integer restTimeSeconds;
    private String instructions;

    // Constructors
    public ExerciseRequest() {}

    public ExerciseRequest(String name, String description, Integer sets, Integer reps, Integer restTimeSeconds, String instructions) {
        this.name = name;
        this.description = description;
        this.sets = sets;
        this.reps = reps;
        this.restTimeSeconds = restTimeSeconds;
        this.instructions = instructions;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getSets() { return sets; }
    public void setSets(Integer sets) { this.sets = sets; }

    public Integer getReps() { return reps; }
    public void setReps(Integer reps) { this.reps = reps; }

    public Integer getRestTimeSeconds() { return restTimeSeconds; }
    public void setRestTimeSeconds(Integer restTimeSeconds) { this.restTimeSeconds = restTimeSeconds; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
}
