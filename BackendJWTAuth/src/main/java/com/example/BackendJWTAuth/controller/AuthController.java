package com.example.BackendJWTAuth.controller;


import com.example.BackendJWTAuth.model.AuthRequest;
import com.example.BackendJWTAuth.model.AuthResponse;
import com.example.BackendJWTAuth.model.JwtUser;
import com.example.BackendJWTAuth.model.User;
import com.example.BackendJWTAuth.services.JWTService;
import com.example.BackendJWTAuth.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            JwtUser jwtUser = userService.verify(authRequest, response);
            return ResponseEntity.ok(jwtUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        try {
            userService.registerNewUser(authRequest.getLogin(), authRequest.getPwd(), User.Role.USER);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        return userService.getNewAccessToken(request, response);
    }
}
