// This class is part of the 'security' package
package dev.renancmd.MyOrganizer.security;

// Importing classes to work with JWT (Java Web Token)
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// This annotation makes the class a Spring component, so it can be injected as a dependency
import org.springframework.stereotype.Component;

// Importing Java security and date classes
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // A secret key used to sign the JWT. This should be kept safe and private.
    // You should usually store it in environment variables or a secrets manager.
    private static final String SECRET = "kmSclRjPdFByFzvYVqGshO5mU2tOeR8Q+koMMeM4ExE=";

    // Token expiration time: 24 hours (in milliseconds)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    // Generating a key using the secret. This key is used to sign and verify JWT tokens.
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // This method generates a new JWT token with the user's email as the subject
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) // Set the email as the subject of the token
                .setIssuedAt(new Date()) // Set the issue date to now
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Set the expiration date
                .signWith(key, SignatureAlgorithm.HS256) // Sign the token with the key using HS256 algorithm
                .compact(); // Build the token as a compact string
    }

    // This method extracts the email (subject) from the token
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // Use the same key to validate the signature
                .build()
                .parseClaimsJws(token) // Parse the token and verify its integrity
                .getBody()
                .getSubject(); // Return the subject (email)
    }

    // This method checks if the token is valid
    public boolean isValid(String token) {
        try {
            extractEmail(token); // Try to extract email; if it works, the token is valid
            return true;
        } catch (JwtException e) {
            return false; // If any error occurs (invalid signature, expired token, etc.), return false
        }
    }
}
