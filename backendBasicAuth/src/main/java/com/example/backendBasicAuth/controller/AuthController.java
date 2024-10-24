package com.example.backendBasicAuth.controller;

import com.example.backendBasicAuth.model.AuthRequest;
import com.example.backendBasicAuth.model.AuthResponse;
import com.example.backendBasicAuth.model.User;
import com.example.backendBasicAuth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse res = userService.userLogin(authRequest.getLogin(), authRequest.getPwd(), User.Role.USER);
            return ResponseEntity.ok(res);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        try {
            userService.registerNewUser(authRequest.getLogin(), authRequest.getPwd(), User.Role.USER);
            return ResponseEntity.ok("User registered successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
