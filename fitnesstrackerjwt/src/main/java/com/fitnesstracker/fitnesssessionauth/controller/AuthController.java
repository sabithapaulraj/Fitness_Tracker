package com.fitnesstracker.fitnesssessionauth.controller;

import com.fitnesstracker.fitnesssessionauth.dto.JwtResponse;
import com.fitnesstracker.fitnesssessionauth.dto.LoginRequest;
import com.fitnesstracker.fitnesssessionauth.dto.RegisterRequest;
import com.fitnesstracker.fitnesssessionauth.model.User;
import com.fitnesstracker.fitnesssessionauth.repository.UserRepository;
import com.fitnesstracker.fitnesssessionauth.service.CustomUserDetailsService;
import com.fitnesstracker.fitnesssessionauth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        userRepository.save(user);
        return "redirect:/login";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<?> registerUserApi(@RequestBody RegisterRequest registerRequest) {
        Map<String, String> response = new HashMap<>();
        
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            response.put("error", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setName(registerRequest.getName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(registerRequest.getRole());
        
        userRepository.save(user);
        
        response.put("message", "User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<?> loginUserApi(@RequestBody LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
            User user = userDetailsService.findUserByEmail(loginRequest.getEmail());
            String jwt = jwtUtil.generateToken(userDetails.getUsername(), user.getRole().name());

            return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getName(), user.getRole().name()));
        } catch (BadCredentialsException e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid credentials");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
