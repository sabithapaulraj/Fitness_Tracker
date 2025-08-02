package com.example.fitness_tracker.service;


import com.example.fitness_tracker.model.User;

public interface UserService {
    User registerUser(User user);
    User getUserByEmail(String email);
}
