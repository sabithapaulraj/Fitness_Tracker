# Fitness Tracker JWT API Test Script

# This PowerShell script demonstrates how to test the JWT-based API endpoints

# Base URL
$baseUrl = "http://localhost:8080"

Write-Host "=== Fitness Tracker JWT API Test ===" -ForegroundColor Green

# Test 1: Register a Trainer
Write-Host "`n1. Registering a Trainer..." -ForegroundColor Yellow

$registerTrainer = @{
    email = "john.trainer@example.com"
    name = "John Trainer"
    password = "password123"
    role = "TRAINER"
} | ConvertTo-Json

try {
    $registerResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/api/register" -Method Post -Body $registerTrainer -ContentType "application/json"
    Write-Host "✓ Trainer registered successfully" -ForegroundColor Green
    Write-Host $registerResponse
} catch {
    Write-Host "✗ Trainer registration failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Register a User
Write-Host "`n2. Registering a User..." -ForegroundColor Yellow

$registerUser = @{
    email = "jane.user@example.com"
    name = "Jane User"
    password = "password123"
    role = "USER"
} | ConvertTo-Json

try {
    $userResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/api/register" -Method Post -Body $registerUser -ContentType "application/json"
    Write-Host "✓ User registered successfully" -ForegroundColor Green
    Write-Host $userResponse
} catch {
    Write-Host "✗ User registration failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Login as Trainer and get JWT token
Write-Host "`n3. Logging in as Trainer..." -ForegroundColor Yellow

$loginTrainer = @{
    email = "john.trainer@example.com"
    password = "password123"
} | ConvertTo-Json

try {
    $loginResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/api/login" -Method Post -Body $loginTrainer -ContentType "application/json"
    $trainerToken = $loginResponse.token
    Write-Host "✓ Trainer login successful" -ForegroundColor Green
    Write-Host "Token: $($trainerToken.Substring(0, 50))..." -ForegroundColor Cyan
    Write-Host "Role: $($loginResponse.role)" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Trainer login failed: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
}

# Test 4: Create a Workout Plan
Write-Host "`n4. Creating a Workout Plan..." -ForegroundColor Yellow

$workoutPlan = @{
    name = "Beginner Strength Training"
    description = "A comprehensive strength training program for beginners"
} | ConvertTo-Json

$headers = @{
    "Authorization" = "Bearer $trainerToken"
    "Content-Type" = "application/json"
}

try {
    $planResponse = Invoke-RestMethod -Uri "$baseUrl/api/plans" -Method Post -Body $workoutPlan -Headers $headers
    $planId = $planResponse.id
    Write-Host "✓ Workout plan created successfully" -ForegroundColor Green
    Write-Host "Plan ID: $planId" -ForegroundColor Cyan
    Write-Host "Plan Name: $($planResponse.name)" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Workout plan creation failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 5: Add Exercise to Workout Plan
Write-Host "`n5. Adding Exercise to Workout Plan..." -ForegroundColor Yellow

$exercise = @{
    name = "Push-ups"
    description = "Standard push-ups for upper body strength"
    sets = 3
    reps = 10
    restTimeSeconds = 60
    instructions = "Keep your body straight and lower until chest touches the ground"
} | ConvertTo-Json

try {
    $exerciseResponse = Invoke-RestMethod -Uri "$baseUrl/api/plans/$planId/exercises" -Method Post -Body $exercise -Headers $headers
    Write-Host "✓ Exercise added successfully" -ForegroundColor Green
    Write-Host "Exercise: $($exerciseResponse.name)" -ForegroundColor Cyan
    Write-Host "Sets: $($exerciseResponse.sets), Reps: $($exerciseResponse.reps)" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Exercise addition failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 6: Get All Workout Plans
Write-Host "`n6. Getting All Workout Plans..." -ForegroundColor Yellow

try {
    $allPlans = Invoke-RestMethod -Uri "$baseUrl/api/plans" -Method Get -Headers $headers
    Write-Host "✓ Retrieved workout plans successfully" -ForegroundColor Green
    Write-Host "Number of plans: $($allPlans.Count)" -ForegroundColor Cyan
    foreach ($plan in $allPlans) {
        Write-Host "- $($plan.name): $($plan.description)" -ForegroundColor White
    }
} catch {
    Write-Host "✗ Failed to retrieve workout plans: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 7: Get Exercises for the Workout Plan
Write-Host "`n7. Getting Exercises for Workout Plan..." -ForegroundColor Yellow

try {
    $exercises = Invoke-RestMethod -Uri "$baseUrl/api/plans/$planId/exercises" -Method Get -Headers $headers
    Write-Host "✓ Retrieved exercises successfully" -ForegroundColor Green
    Write-Host "Number of exercises: $($exercises.Count)" -ForegroundColor Cyan
    foreach ($ex in $exercises) {
        Write-Host "- $($ex.name): $($ex.sets) sets x $($ex.reps) reps" -ForegroundColor White
    }
} catch {
    Write-Host "✗ Failed to retrieve exercises: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 8: Login as User and try to access trainer endpoints (should fail)
Write-Host "`n8. Testing User Access Restrictions..." -ForegroundColor Yellow

$loginUser = @{
    email = "jane.user@example.com"
    password = "password123"
} | ConvertTo-Json

try {
    $userLoginResponse = Invoke-RestMethod -Uri "$baseUrl/api/auth/api/login" -Method Post -Body $loginUser -ContentType "application/json"
    $userToken = $userLoginResponse.token
    
    $userHeaders = @{
        "Authorization" = "Bearer $userToken"
        "Content-Type" = "application/json"
    }
    
    # Try to create a workout plan as user (should fail)
    try {
        $userPlanAttempt = Invoke-RestMethod -Uri "$baseUrl/api/plans" -Method Post -Body $workoutPlan -Headers $userHeaders
        Write-Host "✗ User was able to create workout plan (SECURITY ISSUE)" -ForegroundColor Red
    } catch {
        Write-Host "✓ User correctly denied access to create workout plans" -ForegroundColor Green
    }
} catch {
    Write-Host "✗ User login failed: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 9: Get User Dashboard
Write-Host "`n9. Getting User Dashboard..." -ForegroundColor Yellow

try {
    $dashboard = Invoke-RestMethod -Uri "$baseUrl/api/dashboard" -Method Get -Headers $userHeaders
    Write-Host "✓ User dashboard retrieved successfully" -ForegroundColor Green
    Write-Host "User: $($dashboard.user.name) ($($dashboard.user.role))" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Failed to retrieve user dashboard: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== Test Complete ===" -ForegroundColor Green
Write-Host "All major JWT authentication and authorization features have been tested." -ForegroundColor White
