package com.example.BackendJWTAuth.controller;

import com.example.BackendJWTAuth.model.User;
import com.example.BackendJWTAuth.services.UserService;
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

    @CrossOrigin(origins = "*")
    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        return userService.deleteUser(id);
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