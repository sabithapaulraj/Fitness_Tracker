package com.fitnesstracker.fitnesssessionauth.util;

import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class to generate secure JWT secret keys
 * Run this class to generate a new secret key for production use
 */
public class JwtSecretGenerator {
    
    public static void main(String[] args) {
        System.out.println("=== JWT Secret Key Generator ===");
        System.out.println();
        
        // Generate different sizes of secret keys
        generateSecret("256-bit (32 bytes) - Recommended for HS256", 32);
        generateSecret("384-bit (48 bytes) - For HS384", 48);
        generateSecret("512-bit (64 bytes) - For HS512", 64);
        
        System.out.println("\n=== Usage Instructions ===");
        System.out.println("1. Copy one of the generated secrets above");
        System.out.println("2. Replace the value of 'app.jwt.secret' in application.properties");
        System.out.println("3. For production, consider using environment variables:");
        System.out.println("   export JWT_SECRET=your_generated_secret");
        System.out.println("   app.jwt.secret=${JWT_SECRET:fallback_secret}");
        System.out.println();
        System.out.println("=== Security Notes ===");
        System.out.println("- Never commit production secrets to version control");
        System.out.println("- Use different secrets for different environments");
        System.out.println("- Rotate secrets periodically");
        System.out.println("- Store secrets securely (Azure Key Vault, AWS Secrets Manager, etc.)");
    }
    
    private static void generateSecret(String description, int bytes) {
        SecureRandom random = new SecureRandom();
        byte[] secretBytes = new byte[bytes];
        random.nextBytes(secretBytes);
        
        String base64Secret = Base64.getEncoder().encodeToString(secretBytes);
        String hexSecret = bytesToHex(secretBytes);
        
        System.out.println(description + ":");
        System.out.println("Base64: " + base64Secret);
        System.out.println("Hex:    " + hexSecret);
        System.out.println();
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
