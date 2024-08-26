package com.example.BackendJWTAuth.services;

import com.example.BackendJWTAuth.model.AuthRequest;
import com.example.BackendJWTAuth.model.AuthResponse;
import com.example.BackendJWTAuth.model.User;
import com.example.BackendJWTAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    AuthenticationManager authManager;

    public User registerNewUser(String username, String password, User.Role role) {
        if (userRepository.existsByName(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
//        User user = new User(username, passwordEncoder.encode(password), role);

        User user = new User(username, password, role);
        return userRepository.save(user);
    }

    public AuthResponse userLogin(String username, String password, User.Role role) {
        if (userRepository.existsByName(username)) {
            User user = userRepository.foundUser(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//            throw new IllegalArgumentException("Unsuccessfully login" + user.getPassword() + "   " + password);

            if(Objects.equals(user.getPassword(), password)){
                AuthResponse res = new AuthResponse();
                res.copyFrom(user);
                return res;
            }
        }

        throw new IllegalArgumentException("Unsuccessfully login");
    }

    public String verify(AuthRequest user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPwd()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getLogin());
        } else {
            return "fail";
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> deleteUser(String name) {
        if (userRepository.existsByName(name)) {
            userRepository.deleteByName(name);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    public ResponseEntity<String> updateUser(
            @PathVariable Integer id, String name, String password) {

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

    public ResponseEntity<String> updateRole(Integer id, User.Role role) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User user = userOptional.get();
        user.setRole(role);

        userRepository.save(user); // Save the updated user entity

        return ResponseEntity.ok("User role updated successfully");
    }
}
