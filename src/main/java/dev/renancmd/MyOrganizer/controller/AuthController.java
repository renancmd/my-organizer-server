package dev.renancmd.MyOrganizer.controller;

import dev.renancmd.MyOrganizer.dto.*;
import dev.renancmd.MyOrganizer.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

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

}
