package com.fitnesstracker.fitnesssessionauth.controller;

import com.fitnesstracker.fitnesssessionauth.model.User;
import com.fitnesstracker.fitnesssessionauth.repository.UserRepository;
import com.fitnesstracker.fitnesssessionauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DashboardController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Web endpoint for dashboard (Thymeleaf template)
    @GetMapping("/api/user/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "dashboard";
    }

    // Alternative dashboard endpoint
    @GetMapping("/dashboard")
    public String dashboardAlt(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "dashboard";
    }

    // REST API endpoint for dashboard data
    @GetMapping("/api/dashboard")
    @ResponseBody
    public ResponseEntity<?> getDashboardData(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        response.put("user", Map.of(
            "id", user.getId(),
            "email", user.getEmail(),
            "name", user.getName(),
            "role", user.getRole().name()
        ));
        response.put("message", "Welcome to your dashboard");
        
        return ResponseEntity.ok(response);
    }
}
