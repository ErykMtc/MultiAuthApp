package com.authapp.backend.controller;

import com.authapp.backend.model.AuthRequest;
import com.authapp.backend.model.AuthResponse;
import com.authapp.backend.model.User;
import com.authapp.backend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


//    @GetMapping("/")
//    public String handleLogin(){
//        return "ddd";
//    }

    @PostMapping("/")
    public ResponseEntity<?> handleLogin(@RequestBody AuthRequest req){
        if(req.getLogin() == null || req.getPwd() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }

        User user =  userRepository.foundUser(req.getLogin());
        if(user == null || !Objects.equals(user.getPassword(), req.getPwd())){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        AuthResponse res = new AuthResponse();
        res.copyFrom(user);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/unseclogin")
    public ResponseEntity<?> handleUnsecLogin(@RequestBody AuthRequest req) {
        if (req.getLogin() == null || req.getPwd() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }

        // Użycie niebezpiecznej metody z SQL Injection
        List<User> users = userService.unSecFindUserByName(req.getLogin());

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // Możesz zwrócić listę użytkowników, lub odpowiedni wynik w zależności od kontekstu
        return ResponseEntity.ok(users);
    }


    @PostMapping("/register")
    public ResponseEntity<String> registration(@Valid @RequestBody AuthRequest authRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors() || authRequest.getLogin() == null || authRequest.getPwd() == null) {
            // Jeśli walidacja danych wejściowych nie powiodła się, zwróć błąd 400 (Bad Request) i listę błędów
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data: " + bindingResult.getAllErrors());
        } else {
            if (userRepository.existsByName(authRequest.getLogin())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User with this name already exists");
            } else {
                User user = new User(authRequest.getLogin(), authRequest.getPwd(), User.Role.USER);
                userRepository.save(user);
                return ResponseEntity.ok("User registered successfully");
            }
        }
    }

}
