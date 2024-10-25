package com.example.BackendJWTAuth.services;

import com.example.BackendJWTAuth.model.AuthRequest;
import com.example.BackendJWTAuth.model.AuthResponse;
import com.example.BackendJWTAuth.model.JwtUser;
import com.example.BackendJWTAuth.model.User;
import com.example.BackendJWTAuth.repository.UserRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

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

    public AuthResponse userLogin(String username, String password) {
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

    public JwtUser verify(AuthRequest authRequest, HttpServletResponse response) {
        if (userRepository.existsByName(authRequest.getLogin())) {
            User user = userRepository.foundUser(authRequest.getLogin())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPwd()));
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                JwtUser jwtUser = new JwtUser();
                jwtUser.setToken(jwtService.generateToken(userDetails));
                jwtUser.setRefreshToken(jwtService.generateRefreshToken(userDetails));
                
                jwtUser.copyFrom(user);

                userRepository.updateRefreshToken(jwtUser.getId(), jwtUser.getRefreshToken());

                // Add the refresh token as an HTTP-only cookie
                Cookie refreshTokenCookie = new Cookie("refreshToken", jwtUser.getRefreshToken());
                refreshTokenCookie.setHttpOnly(true);  // Prevents JavaScript access to the cookie
                refreshTokenCookie.setSecure(true);    // Only send over HTTPS
                refreshTokenCookie.setPath("/");       // Cookie will be sent to all paths in the domain
                refreshTokenCookie.setMaxAge(60 * 60 * 24 * 7); // 1 week expiry

                response.addCookie(refreshTokenCookie);

                return jwtUser;
            }
        }
        throw new IllegalArgumentException("Unsuccessfully login");
    }

    public ResponseEntity<?> getNewAccessToken(HttpServletRequest request, HttpServletResponse response){

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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token is missing");
        }

        Optional<User> userOptional = getUserFromRefreshToken(refreshToken);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
        }

        User user = userOptional.get();
        String newAccessToken = "";

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            if (!jwtService.validateRefreshToken(refreshToken, userDetails)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
            }

            newAccessToken = jwtService.generateToken(userDetails);
        }

        HashMap<String, Object> responseBody = new HashMap<>();

        responseBody.put("accessToken", newAccessToken);
        responseBody.put("userid", user.getId());
        responseBody.put("role", user.getRole());

        return ResponseEntity.ok(responseBody);
    }

    public Optional<User> getUserFromRefreshToken(String refreshToken) {
        return userRepository.findByRefreshToken(refreshToken);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public ResponseEntity<String> deleteUser(Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
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

        userRepository.save(user);

        return ResponseEntity.ok("User role updated successfully");
    }
}
