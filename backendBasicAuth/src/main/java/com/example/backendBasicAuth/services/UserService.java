package com.example.backendBasicAuth.services;

import com.example.backendBasicAuth.model.User;
import com.example.backendBasicAuth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(String username, String password, User.Role role) {
        if (userRepository.existsByName(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
//        User user = new User(username, passwordEncoder.encode(password), role);

        User user = new User(username, password, role);
        return userRepository.save(user);
    }

    public User userLogin(String username, String password, User.Role role) {
        if (userRepository.existsByName(username)) {
            User user = userRepository.foundUser(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//            throw new IllegalArgumentException("Unsuccessfully login" + user.getPassword() + "   " + password);

            if(Objects.equals(user.getPassword(), password)){
                return user;
            }
        }

        throw new IllegalArgumentException("Unsuccessfully login");
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
