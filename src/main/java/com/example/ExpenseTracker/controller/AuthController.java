package com.example.ExpenseTracker.controller;

import com.example.ExpenseTracker.authdto.LoginRequestDTO;
import com.example.ExpenseTracker.authdto.LoginResponseDTO;
import com.example.ExpenseTracker.authdto.RegisterRequestDTO;
import com.example.ExpenseTracker.configuration.JwtTokenProvider;
import com.example.ExpenseTracker.services.UserService;
import com.example.ExpenseTracker.userdto.UserRequestDTO;
import com.example.ExpenseTracker.userdto.UserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        UserRequestDTO userRequest = new UserRequestDTO(
                request.getUsername(),
                request.getPassword(),
                request.getEmail()
        );
        UserResponseDTO createdUser = userService.createUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
