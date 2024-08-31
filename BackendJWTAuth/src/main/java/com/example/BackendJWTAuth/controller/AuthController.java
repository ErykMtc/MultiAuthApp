package com.example.BackendJWTAuth.controller;


import com.example.BackendJWTAuth.model.AuthRequest;
import com.example.BackendJWTAuth.model.AuthResponse;
import com.example.BackendJWTAuth.model.JwtUser;
import com.example.BackendJWTAuth.model.User;
import com.example.BackendJWTAuth.services.UserService;
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
            JwtUser jwtUser = userService.verify(authRequest);
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
}
