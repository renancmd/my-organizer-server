package dev.renancmd.MyOrganizer.service;

import dev.renancmd.MyOrganizer.dto.ChangePasswordDTO;
import dev.renancmd.MyOrganizer.dto.LoginDTO;
import dev.renancmd.MyOrganizer.dto.RegisterDTO;
import dev.renancmd.MyOrganizer.dto.UpdateUserDTO;
import dev.renancmd.MyOrganizer.model.User;
import dev.renancmd.MyOrganizer.repository.UserRepository;
import dev.renancmd.MyOrganizer.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public void updateUser(String email, UpdateUserDTO dto) {
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userOptional.get();

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        userRepository.save(user);
    }

    public void changePassword(String email, ChangePasswordDTO dto) {
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password does not match");
        }

        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        userRepository.save(user);
    }

}
