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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/update")
    public String showUpdateForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", user);
        return "update-profile";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute User updatedUser, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
        user.setName(updatedUser.getName());
        userRepository.save(user);
        return "redirect:/api/user/dashboard";
    }

    @GetMapping("/api/profile")
    @ResponseBody
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userDetailsService.findUserByEmail(userDetails.getUsername());
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("name", user.getName());
        response.put("role", user.getRole().name());
        
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/profile")
    @ResponseBody
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> updates, 
                                         @AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userDetailsService.findUserByEmail(userDetails.getUsername());
            
            if (updates.containsKey("name")) {
                user.setName(updates.get("name"));
            }
            userRepository.save(user);
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Profile updated successfully");
            response.put("user", Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                "name", user.getName(),
                "role", user.getRole().name()
            ));
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to update profile: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
