# Test Database Connection and Check Users

This PowerShell script helps you check if there are users in your database and test the login functionality.

## Step 1: Check if you have any users registered

First, make sure you have registered at least one user. If you haven't:

1. Go to: http://localhost:8080/api/auth/register
2. Fill out the form with:
   - Name: Test User
   - Email: test@example.com
   - Password: password123
   - Role: USER (or TRAINER)

## Step 2: Test the Login Process

1. Go to: http://localhost:8080/login
2. Use the credentials you registered:
   - Email: test@example.com
   - Password: password123
3. Click "Login"
4. You should be redirected to: http://localhost:8080/api/user/dashboard

## Step 3: If login still fails, check these:

### Check MySQL Database:
```sql
USE fitness_jwt_authentication;
SELECT * FROM users;
```

### Check Application Logs:
Look for any error messages in your Spring Boot console output.

### Common Issues:
1. **No users registered** - Register a user first
2. **Wrong credentials** - Make sure email/password are correct
3. **Database connection** - Ensure MySQL is running
4. **Role issues** - Check if user has proper role assigned

## Step 4: Manual Database Insert (if needed)

If registration isn't working, you can manually insert a user:

```sql
USE fitness_jwt_authentication;
INSERT INTO users (email, name, password, role) 
VALUES ('test@example.com', 'Test User', '$2a$10$example_encrypted_password', 'USER');
```

Note: You'll need to encrypt the password using BCrypt first.

## Debugging Tips:

1. Check browser developer tools (F12) -> Console for any JavaScript errors
2. Check browser developer tools -> Network tab to see HTTP requests/responses
3. Look at Spring Boot application logs for authentication errors
4. Verify the redirect URL is correct in browser address bar
