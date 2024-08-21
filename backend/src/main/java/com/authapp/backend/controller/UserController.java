package com.authapp.backend.controller;

import com.authapp.backend.model.AuthRequest;
import com.authapp.backend.model.User;
import com.authapp.backend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteByID(id);
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String password) {

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

    @PutMapping("/role/{id}")
    public ResponseEntity<String> updateRole(@PathVariable Integer id, @RequestParam User.Role role) {
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
