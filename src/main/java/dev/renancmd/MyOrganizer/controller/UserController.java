package dev.renancmd.MyOrganizer.controller;

import dev.renancmd.MyOrganizer.dto.ChangePasswordDTO;
import dev.renancmd.MyOrganizer.dto.DeleteUserDTO;
import dev.renancmd.MyOrganizer.dto.UpdateUserDTO;
import dev.renancmd.MyOrganizer.model.User;
import dev.renancmd.MyOrganizer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthService authService;

    @GetMapping("/me")
    public String me() {
        return "Hello World";
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getEmail();
        authService.updateUser(email, dto);
        return ResponseEntity.ok("User updated successfully");
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String email = user.getEmail();
        authService.changePassword(email, dto);
        return ResponseEntity.ok("Password changed successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody DeleteUserDTO dto, Authentication authentication) {
        System.out.print("Req got it");
        User user = (User) authentication.getPrincipal();
        String email = user.getEmail();
        authService.deleteUser(email, dto);
        return ResponseEntity.ok("User deleted successfully");
    }
}
