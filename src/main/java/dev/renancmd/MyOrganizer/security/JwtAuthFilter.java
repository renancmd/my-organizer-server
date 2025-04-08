// This class is part of the 'security' package
package dev.renancmd.MyOrganizer.security;

// Importing the UserRepository to fetch user data from the database
import dev.renancmd.MyOrganizer.repository.UserRepository;

// Importing necessary classes for handling HTTP requests and responses
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Lombok annotation to generate a constructor with required (final) fields
import lombok.RequiredArgsConstructor;

// Spring Security classes for handling authentication
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

// Marking this class as a Spring component, so it can be managed by the Spring container
import org.springframework.stereotype.Component;

// A filter that runs once per request
import org.springframework.web.filter.OncePerRequestFilter;

// Exception handling
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    // Dependency to handle JWT token operations (like validation, extracting email, etc.)
    private final JwtUtil jwtUtil;

    // Dependency to access user data from the database
    private final UserRepository userRepository;

    // This method is called for every request that comes into the application
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, jakarta.servlet.ServletException {

        // Get the Authorization header from the incoming HTTP request
        String authHeader = request.getHeader("Authorization");

        // Check if the header is present and starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the JWT token (remove "Bearer " prefix)
            String token = authHeader.substring(7);

            // Extract the email (subject) from the token
            String email = jwtUtil.extractEmail(token);

            // If an email is successfully extracted and the token is valid
            if (email != null && jwtUtil.isValid(token)) {
                // Search for the user in the database using the extracted email
                var user = userRepository.findByEmail(email);

                // If user is found, set the authentication in the SecurityContext
                user.ifPresent(u -> {
                    // Create an authentication token (no credentials or authorities here)
                    var authToken = new UsernamePasswordAuthenticationToken(
                            u, null, null);

                    // Add details of the request (IP, session, etc.)
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Set the authenticated user into the current security context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
            }
        }

        // Continue the filter chain, passing the request and response to the next filter
        filterChain.doFilter(request, response);
    }
}
