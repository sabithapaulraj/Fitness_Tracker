package com.fitnesstracker.fitnesssessionauth.dto;

import com.fitnesstracker.fitnesssessionauth.model.User;

public class RegisterRequest {
    private String email;
    private String name;
    private String password;
    private User.Role role = User.Role.USER;

    // Constructors
    public RegisterRequest() {}

    public RegisterRequest(String email, String name, String password, User.Role role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public User.Role getRole() { return role; }
    public void setRole(User.Role role) { this.role = role; }
}
