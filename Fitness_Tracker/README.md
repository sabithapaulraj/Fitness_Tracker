# Fitness Tracker Application

A simple Spring Boot REST API application for managing fitness tracking with user registration, workout plans, and progress reports.

## Features

### 1. User Registration & Authentication
- Users can register with unique email addresses
- Role-based access (USER, TRAINER)
- Password encryption using BCrypt
- Simple authentication without JWT complexity

### 2. Workout Plan Management
- Trainers can create structured workout plans
- Each plan contains multiple exercises with sets, reps, and rest time
- Only trainers can create workout plans (validation enforced)
- CRUD operations for exercises

### 3. Progress Reporting
- Weekly progress reports showing calories burned and exercises completed
- Comparison with previous week's performance
- DTOs used for clean data transfer
- Service layer calculations

## API Endpoints

### Authentication
```
POST /api/auth/register - Register new user
POST /api/auth/login - User login
```

### Exercises
```
GET /api/exercises/all - Get all exercises
POST /api/exercises/add - Create new exercise
GET /api/exercises/{id} - Get exercise by ID
PUT /api/exercises/{id} - Update exercise
DELETE /api/exercises/{id} - Delete exercise
```

### Workout Plans
```
GET /api/plans/all - Get all workout plans
POST /api/plans/add?trainerId={id} - Create workout plan (trainers only)
GET /api/plans/trainer/{trainerId} - Get plans by trainer
```

### Progress Reports
```
POST /api/reports/generate/{userId} - Generate weekly report
GET /api/reports/user/{userId} - Get user's reports
```

## Database Schema

### User
- id (Long)
- username (String)
- email (String, unique)
- password (String, encrypted)
- role (Enum: USER, TRAINER)

### Exercise
- id (Long)
- name (String)
- sets (int)
- reps (int)
- restTimeInSeconds (int)
- caloriesBurned (int)

### WorkoutPlan
- id (Long)
- name (String)
- description (String)
- trainerId (Long)
- exercises (List<Exercise>)

### ProgressReport
- id (Long)
- user (User)
- weekStart (LocalDate)
- caloriesBurned (int)
- exercisesCompleted (int)

## Running the Application

1. Ensure Java 17+ is installed
2. Navigate to project directory
3. Run: `./mvnw spring-boot:run` (Linux/Mac) or `mvnw.cmd spring-boot:run` (Windows)
4. Application starts on http://localhost:8080
5. H2 Console available at http://localhost:8080/h2-console

## Sample Data

The application includes sample data:
- Trainer: trainer@example.com / password
- User: user@example.com / password
- Sample exercises: Push-ups, Squats, Running
- Sample workout plan: Beginner Fitness Plan

## Testing the APIs

### Register a new trainer:
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Trainer",
    "email": "john@example.com",
    "password": "password123",
    "role": "TRAINER"
  }'
```

### Create a workout plan:
```bash
curl -X POST "http://localhost:8080/api/plans/add?trainerId=1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Advanced Plan",
    "description": "High intensity workout",
    "exercises": [
      {
        "name": "Burpees",
        "sets": 3,
        "reps": 10,
        "restTimeInSeconds": 120,
        "caloriesBurned": 100
      }
    ]
  }'
```

### Generate progress report:
```bash
curl -X POST http://localhost:8080/api/reports/generate/2
```

## Key Design Decisions

1. **Simplified Security**: Removed complex JWT implementation for class assignment simplicity
2. **H2 Database**: In-memory database for easy setup and testing
3. **Role-based Validation**: Business logic ensures only trainers create workout plans
4. **DTOs for Reports**: Clean separation between internal models and API responses
5. **Service Layer Calculations**: Business logic for progress comparisons in service layer

## Project Structure

```
src/main/java/com/example/fitness_tracker/
├── config/
│   ├── DataInitializer.java
│   └── SecurityConfig.java
├── controller/
│   ├── AuthController.java
│   ├── ExerciseController.java
│   ├── ProgressReportController.java
│   └── WorkoutPlanController.java
├── dto/
│   ├── AuthRequest.java
│   ├── ProgressReportDTO.java
│   ├── RegisterRequest.java
│   └── WorkoutPlanDTO.java
├── model/
│   ├── Exercise.java
│   ├── ProgressReport.java
│   ├── User.java
│   └── WorkoutPlan.java
├── repository/
│   ├── ExerciseRepository.java
│   ├── ProgressReportRepository.java
│   ├── UserRepository.java
│   └── WorkoutPlanRepository.java
├── service/
│   ├── ProgressReportService.java
│   ├── ProgressReportServiceImpl.java
│   ├── UserService.java
│   ├── UserServiceImpl.java
│   ├── WorkoutPlanService.java
│   └── WorkoutPlanServiceImpl.java
└── FitnessTrackerApplication.java
```

This implementation fulfills all the requirements while keeping the complexity appropriate for a class assignment.
