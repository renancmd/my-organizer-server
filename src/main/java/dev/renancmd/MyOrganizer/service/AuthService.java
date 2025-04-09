package dev.renancmd.MyOrganizer.service;

import dev.renancmd.MyOrganizer.dto.*;
import dev.renancmd.MyOrganizer.model.User;
import dev.renancmd.MyOrganizer.repository.UserRepository;
import dev.renancmd.MyOrganizer.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String register(RegisterDTO dto) {
        // Verify if email already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return "Email already exists";
        }

        // Create a new User object from DTO
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        // Save entity user in database
        userRepository.save(user);
        return "Registration successful";
    }

    public String login(LoginDTO dto) {
        // Finds user by email, checks if password matches; if valid, returns success message, otherwise returns invalid credentials.
        return userRepository.findByEmail(dto.getEmail())
                .filter(user -> passwordEncoder.matches(dto.getPassword(), user.getPassword()))
                .map(user -> jwtUtil.generateToken(user.getEmail()))
                .orElse("Login failed");
    }

}
