package dev.renancmd.MyOrganizer.service;

import dev.renancmd.MyOrganizer.dto.ChangePasswordDTO;
import dev.renancmd.MyOrganizer.dto.DeleteUserDTO;
import dev.renancmd.MyOrganizer.dto.UpdateUserDTO;
import dev.renancmd.MyOrganizer.model.User;
import dev.renancmd.MyOrganizer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public void deleteUser(String email, DeleteUserDTO dto) {
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        userRepository.delete(user);

    }
}
