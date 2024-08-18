package com.example.backendBasicAuth.controller;

import com.example.backendBasicAuth.model.User;
import com.example.backendBasicAuth.repository.UserRepository;
import com.example.backendBasicAuth.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@RequestParam String name) {
        return userService.deleteUser(name);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String password) {

        return userService.updateUser(id, name, password);
    }

    @PutMapping("/role/{id}")
    public ResponseEntity<String> updateRole(@PathVariable Integer id, @RequestParam User.Role role) {
        return userService.updateRole(id, role);
    }

}