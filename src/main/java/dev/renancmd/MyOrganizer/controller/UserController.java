package dev.renancmd.MyOrganizer.controller;

import dev.renancmd.MyOrganizer.dto.*;
import dev.renancmd.MyOrganizer.model.User;
import dev.renancmd.MyOrganizer.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register") // Map the endpoint /auth/register
    public String register(@RequestBody RegisterDTO registerDTO) { // Receive the JSON request
        // and convert automatically to a java object (RegisterDTO)
        return authService.register(registerDTO); // Service that calls the method register to
        // data process | OBS: What is a DTO (line 26 to 30)?
    }

//    A simple object used to transport data between the request and the back-end.
//
//    It is not a JPA entity — it does not go directly to the database.
//
//    It serves as an intermediate layer of security and organization.


    @PostMapping("/login")
    // Receive the data and call login method from service
    public String login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

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
