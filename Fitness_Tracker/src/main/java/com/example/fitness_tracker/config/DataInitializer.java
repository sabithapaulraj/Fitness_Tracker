package com.example.fitness_tracker.config;

import com.example.fitness_tracker.model.Exercise;
import com.example.fitness_tracker.model.User;
import com.example.fitness_tracker.model.WorkoutPlan;
import com.example.fitness_tracker.repository.ExerciseRepository;
import com.example.fitness_tracker.repository.UserRepository;
import com.example.fitness_tracker.repository.WorkoutPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create sample users
        if (userRepository.count() == 0) {
            User trainer = User.builder()
                    .username("John Trainer")
                    .email("trainer@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(User.Role.TRAINER)
                    .build();
            userRepository.save(trainer);

            User user = User.builder()
                    .username("Jane User")
                    .email("user@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(User.Role.USER)
                    .build();
            userRepository.save(user);

            // Create sample exercises
            Exercise pushups = Exercise.builder()
                    .name("Push-ups")
                    .sets(3)
                    .reps(15)
                    .restTimeInSeconds(60)
                    .caloriesBurned(50)
                    .build();

            Exercise squats = Exercise.builder()
                    .name("Squats")
                    .sets(3)
                    .reps(20)
                    .restTimeInSeconds(90)
                    .caloriesBurned(80)
                    .build();

            Exercise running = Exercise.builder()
                    .name("Running")
                    .sets(1)
                    .reps(1)
                    .restTimeInSeconds(0)
                    .caloriesBurned(300)
                    .build();

            exerciseRepository.saveAll(Arrays.asList(pushups, squats, running));

            // Create sample workout plan
            WorkoutPlan beginnerPlan = WorkoutPlan.builder()
                    .name("Beginner Fitness Plan")
                    .description("A basic workout plan for beginners")
                    .trainerId(trainer.getId())
                    .exercises(Arrays.asList(pushups, squats))
                    .build();

            workoutPlanRepository.save(beginnerPlan);

            System.out.println("Sample data created!");
            System.out.println("Trainer login: trainer@example.com / password");
            System.out.println("User login: user@example.com / password");
        }
    }
}
