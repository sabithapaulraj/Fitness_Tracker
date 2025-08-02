package com.fitnesstracker.fitnesssessionauth.controller;

import com.fitnesstracker.fitnesssessionauth.model.User;
import com.fitnesstracker.fitnesssessionauth.model.WorkoutPlan;
import com.fitnesstracker.fitnesssessionauth.repository.UserRepository;
import com.fitnesstracker.fitnesssessionauth.service.WorkoutPlanService;
import com.fitnesstracker.fitnesssessionauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class WorkoutController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutPlanService workoutPlanService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    // Web endpoint for viewing workouts (Thymeleaf template)
    @GetMapping("/workouts")
    public String viewWorkouts(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        
        // Users can view all workout plans but cannot be assigned to them
        if (user.getRole() == User.Role.TRAINER) {
            List<WorkoutPlan> trainerPlans = workoutPlanService.findAllByTrainer(user);
            model.addAttribute("workouts", trainerPlans);
        } else {
            // Regular users see an empty list or a message
            model.addAttribute("workouts", new ArrayList<>());
            model.addAttribute("message", "Contact a trainer to get workout plans assigned to you.");
        }
        
        return "view-workouts";
    }

    // REST API endpoint for getting user's workout information
    @GetMapping("/api/workouts")
    @ResponseBody
    public ResponseEntity<?> getUserWorkouts(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userDetailsService.findUserByEmail(userDetails.getUsername());
        Map<String, Object> response = new HashMap<>();
        
        if (user.getRole() == User.Role.TRAINER) {
            List<WorkoutPlan> trainerPlans = workoutPlanService.findAllByTrainer(user);
            response.put("workouts", trainerPlans);
            response.put("role", "TRAINER");
            response.put("message", "Your created workout plans");
        } else {
            // Users cannot be assigned workout plans by trainers according to requirements
            response.put("workouts", new ArrayList<>());
            response.put("role", "USER");
            response.put("message", "Workout plan assignment is not available for users. Contact admin for assignments.");
        }
        
        return ResponseEntity.ok(response);
    }
}
