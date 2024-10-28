package com.example.BackendOAuth.services;

import com.example.BackendOAuth.model.*;
import com.example.BackendOAuth.repository.UserRepository;
import com.example.BackendOAuth.security.TokenProvider;
import com.example.BackendOAuth.security.WebSecurityConfig;
import com.example.BackendOAuth.security.oauth2.OAuth2Provider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final TokenProvider tokenProvider;
    private UserRepository userRepository;

    public UserService(TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    public User registerNewUser(User user) {
        return userRepository.save(user);
    }

    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> updateRole(Long id, User.Role role) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        user.setRole(role.toString());

        userRepository.save(user);

        return ResponseEntity.ok("User role updated successfully");
    }

    public ResponseEntity<String> deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    public ResponseEntity<String> updateUser(
            @PathVariable Long id, String name, String password) {

        Optional<User> user = userRepository.findById(id);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (name != null && !name.isEmpty()) {
            userRepository.updateName(id, name);
        }

        if (password != null && !password.isEmpty()) {
            userRepository.updatePassword(id, password);
        }

        return ResponseEntity.ok("User updated successfully");
    }

    public ResponseEntity<String> updateRole(Long id, String role) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        user.setRole(role);

        userRepository.save(user);

        return ResponseEntity.ok("User role updated successfully");
    }

    @Transactional
    public String generateToken(String username, Authentication authentication, HttpServletResponse response){
        if(username != null && userRepository.existsByUserName(username)){
            String refreshToken = tokenProvider.generateRefreshJWT(authentication);
            String token = tokenProvider.generate(authentication);
            updateUserRefreshToken(username, refreshToken);

            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setSecure(false);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); 

            response.addCookie(refreshTokenCookie);

            return token;
        }
        throw new IllegalArgumentException("Unsuccessful login");
    }

    public User getNewAccessToken(HttpServletRequest request, HttpServletResponse response){

        Cookie[] cookies = request.getCookies();
        String refreshToken = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new RuntimeException("Refresh token is missing");
        }

        Optional<User> userOptional = getUserFromRefreshToken(refreshToken);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("Invalid refresh token");
        }

        if (!tokenProvider.validateRefreshToken(refreshToken)){
            throw new RuntimeException("Invalid refresh token");
        }

        User user = userOptional.get();
        return user;
    }

    public Optional<User> getUserFromRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    @Transactional
    public void updateUserRefreshToken(String username, String refreshToken) {
        userRepository.updateRefreshToken(username, refreshToken);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
