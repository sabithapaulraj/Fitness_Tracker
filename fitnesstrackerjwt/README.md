# Fitness Tracker with JWT Authentication

This is a Spring Boot application that provides both web-based and REST API access for a fitness tracking system with JWT-based authentication and role-based access control.

## Features

- **JWT-based Authentication**: Secure token-based authentication system
- **Role-based Access Control**: Support for USER, TRAINER, and ADMIN roles
- **Workout Plan Management**: Trainers can create, read, update, and delete workout plans
- **Exercise Management**: Add exercises with sets, reps, and rest times to workout plans
- **Security**: Trainers cannot assign workout plans to users (admin-only feature)
- **Dual Interface**: Both web UI and REST API support

## Entities and Relationships

### User Entity
- `id` (Long): Primary key
- `email` (String): Unique email address
- `name` (String): User's full name
- `password` (String): Encrypted password
- `role` (Enum): USER, TRAINER, or ADMIN

### WorkoutPlan Entity
- `id` (Long): Primary key
- `name` (String): Plan name
- `description` (String): Plan description
- `createdAt` (LocalDateTime): Creation timestamp
- `updatedAt` (LocalDateTime): Last update timestamp
- `trainer` (User): The trainer who created the plan
- `exercises` (List<Exercise>): List of exercises in the plan

### Exercise Entity
- `id` (Long): Primary key
- `name` (String): Exercise name
- `description` (String): Exercise description
- `sets` (Integer): Number of sets
- `reps` (Integer): Number of repetitions
- `restTimeSeconds` (Integer): Rest time between sets
- `instructions` (String): Exercise instructions
- `workoutPlan` (WorkoutPlan): The workout plan this exercise belongs to

## API Endpoints

### Authentication Endpoints

#### Register User (API)
```
POST /api/auth/api/register
Content-Type: application/json

{
  "email": "trainer@example.com",
  "name": "John Trainer",
  "password": "password123",
  "role": "TRAINER"
}
```

#### Login (API) - Returns JWT Token
```
POST /api/auth/api/login
Content-Type: application/json

{
  "email": "trainer@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "email": "trainer@example.com",
  "name": "John Trainer",
  "role": "TRAINER"
}
```

### Workout Plan Management (Trainers Only)

All workout plan endpoints require `Authorization: Bearer <token>` header.

#### Create Workout Plan
```
POST /api/plans
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Beginner Strength Training",
  "description": "A basic strength training program for beginners"
}
```

#### Get All Workout Plans (for authenticated trainer)
```
GET /api/plans
Authorization: Bearer <jwt-token>
```

#### Get Specific Workout Plan
```
GET /api/plans/{id}
Authorization: Bearer <jwt-token>
```

#### Update Workout Plan
```
PUT /api/plans/{id}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Updated Plan Name",
  "description": "Updated description"
}
```

#### Delete Workout Plan
```
DELETE /api/plans/{id}
Authorization: Bearer <jwt-token>
```

### Exercise Management

#### Add Exercise to Workout Plan
```
POST /api/plans/{planId}/exercises
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Push-ups",
  "description": "Standard push-ups",
  "sets": 3,
  "reps": 10,
  "restTimeSeconds": 60,
  "instructions": "Keep your body straight and lower until chest touches the ground"
}
```

#### Get Exercises for a Workout Plan
```
GET /api/plans/{planId}/exercises
Authorization: Bearer <jwt-token>
```

#### Update Exercise
```
PUT /api/plans/{planId}/exercises/{exerciseId}
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Modified Push-ups",
  "description": "Modified push-ups for beginners",
  "sets": 2,
  "reps": 8,
  "restTimeSeconds": 90,
  "instructions": "Perform on knees if needed"
}
```

#### Delete Exercise
```
DELETE /api/plans/{planId}/exercises/{exerciseId}
Authorization: Bearer <jwt-token>
```

### User Profile Endpoints

#### Get User Profile (API)
```
GET /api/user/api/profile
Authorization: Bearer <jwt-token>
```

#### Update User Profile (API)
```
PUT /api/user/api/profile
Authorization: Bearer <jwt-token>
Content-Type: application/json

{
  "name": "Updated Name"
}
```

#### Get Dashboard Data (API)
```
GET /api/dashboard
Authorization: Bearer <jwt-token>
```

#### Get User Workouts (API)
```
GET /api/user/api/workouts
Authorization: Bearer <jwt-token>
```

## Web Interface Endpoints

The application also provides a traditional web interface:

- `/` - Home page (redirects to login)
- `/login` - Login form
- `/api/auth/register` - Registration form
- `/api/user/dashboard` - User dashboard
- `/api/user/profile` - User profile view
- `/api/user/update` - Update profile form
- `/api/user/workouts` - View workouts page

## Security Features

### Role-Based Access Control
- **TRAINER**: Can create, read, update, and delete their own workout plans and exercises
- **USER**: Can view their profile and dashboard, cannot create workout plans
- **ADMIN**: Full access (implementation ready for future extension)

### JWT Token Security
- Tokens expire after 24 hours
- Tokens include role information for authorization
- Secure token validation on every request

### Access Restrictions
- Trainers can only access their own workout plans
- Users cannot assign workout plans to themselves
- Unauthorized access returns HTTP 403 Forbidden

## Getting Started

### Prerequisites
- Java 17 or later
- MySQL 8.0
- Maven 3.6+

### Setup

1. **Database Setup**
   ```sql
   CREATE DATABASE fitness_jwt_authentication;
   ```

2. **Configure Application**
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/fitness_jwt_authentication
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**
   - Web Interface: http://localhost:8080
   - API Base URL: http://localhost:8080/api

### Testing the API

1. **Register a Trainer**
   ```bash
   curl -X POST http://localhost:8080/api/auth/api/register \
     -H "Content-Type: application/json" \
     -d '{
       "email": "trainer@example.com",
       "name": "John Trainer",
       "password": "password123",
       "role": "TRAINER"
     }'
   ```

2. **Login and Get JWT Token**
   ```bash
   curl -X POST http://localhost:8080/api/auth/api/login \
     -H "Content-Type: application/json" \
     -d '{
       "email": "trainer@example.com",
       "password": "password123"
     }'
   ```

3. **Create a Workout Plan**
   ```bash
   curl -X POST http://localhost:8080/api/plans \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Beginner Strength",
       "description": "A beginner-friendly strength training program"
     }'
   ```

4. **Add Exercises**
   ```bash
   curl -X POST http://localhost:8080/api/plans/1/exercises \
     -H "Authorization: Bearer YOUR_JWT_TOKEN" \
     -H "Content-Type: application/json" \
     -d '{
       "name": "Push-ups",
       "description": "Standard push-ups",
       "sets": 3,
       "reps": 10,
       "restTimeSeconds": 60
     }'
   ```

## Project Structure

```
src/
├── main/
│   ├── java/com/fitnesstracker/fitnesssessionauth/
│   │   ├── config/
│   │   │   └── SecurityConfig.java
│   │   ├── controller/
│   │   │   ├── AuthController.java
│   │   │   ├── WorkoutPlanController.java
│   │   │   ├── UserController.java
│   │   │   ├── DashboardController.java
│   │   │   ├── WorkoutController.java
│   │   │   └── LoginController.java
│   │   ├── dto/
│   │   │   ├── LoginRequest.java
│   │   │   ├── RegisterRequest.java
│   │   │   ├── JwtResponse.java
│   │   │   ├── WorkoutPlanRequest.java
│   │   │   └── ExerciseRequest.java
│   │   ├── filter/
│   │   │   └── JwtAuthenticationFilter.java
│   │   ├── model/
│   │   │   ├── User.java
│   │   │   ├── WorkoutPlan.java
│   │   │   └── Exercise.java
│   │   ├── repository/
│   │   │   ├── UserRepository.java
│   │   │   ├── WorkoutPlanRepository.java
│   │   │   └── ExerciseRepository.java
│   │   ├── service/
│   │   │   ├── CustomUserDetailsService.java
│   │   │   ├── WorkoutPlanService.java
│   │   │   └── ExerciseService.java
│   │   ├── util/
│   │   │   └── JwtUtil.java
│   │   └── FitnesstrackerApplication.java
│   └── resources/
│       ├── templates/
│       │   ├── login.html
│       │   ├── register.html
│       │   ├── dashboard.html
│       │   ├── profile.html
│       │   ├── update-profile.html
│       │   └── view-workouts.html
│       └── application.properties
```

## Future Enhancements

- Admin role implementation for user-workout plan assignments
- Workout plan templates and sharing
- Progress tracking and analytics
- Mobile app integration
- Advanced exercise filtering and search
- Workout plan rating and reviews
