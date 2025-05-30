package dev.renancmd.MyOrganizer.controller;

import dev.renancmd.MyOrganizer.dto.ChangePasswordDTO;
import dev.renancmd.MyOrganizer.dto.DeleteUserDTO;
import dev.renancmd.MyOrganizer.dto.UpdateUserDTO;
import dev.renancmd.MyOrganizer.dto.UserInfoDTO;
import dev.renancmd.MyOrganizer.model.User;
import dev.renancmd.MyOrganizer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<List<UserInfoDTO>> showUserData(Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        List<UserInfoDTO> userData = userService.getUser(email);
        return ResponseEntity.ok(userData);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UpdateUserDTO dto, Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        userService.updateUser(email, dto);
        return ResponseEntity.ok("Successfully updated user");
    }

    @PutMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto, Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        userService.changePassword(email, dto);
        return ResponseEntity.ok("Password changed successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody DeleteUserDTO dto, Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        userService.deleteUser(email, dto);
        return ResponseEntity.ok("User deleted successfully");
    }
}
