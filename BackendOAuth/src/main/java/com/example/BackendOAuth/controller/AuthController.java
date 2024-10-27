package com.example.BackendOAuth.controller;

import com.example.BackendOAuth.model.AuthRequest;
import com.example.BackendOAuth.model.SignUpRequest;
import com.example.BackendOAuth.model.User;
import com.example.BackendOAuth.security.TokenProvider;
import com.example.BackendOAuth.security.WebSecurityConfig;
import com.example.BackendOAuth.security.oauth2.OAuth2Provider;
import com.example.BackendOAuth.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, AuthenticationManager authenticationManager, TokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest loginRequest, HttpServletResponse response) {
        String token = authenticateAndGetToken(loginRequest.getLogin(), loginRequest.getPwd(), response);
        return ResponseEntity.ok(token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletResponse response) {
        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        userService.registerNewUser(mapSignUpRequestToUser(signUpRequest));

        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword(), response);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        User user = userService.getNewAccessToken(request, response);

        String newAccessToken = tokenProvider.generateFromRefreshToken(user);

        HashMap<String, Object> responseBody = new HashMap<>();

        responseBody.put("accessToken", newAccessToken);
        responseBody.put("username", user.getUsername());
        responseBody.put("role", user.getRole());
        return ResponseEntity.ok(responseBody);
    }

    private String authenticateAndGetToken(String username, String password, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return userService.generateToken(username, authentication, response);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        user.setProvider(OAuth2Provider.LOCAL);
        return user;
    }


}