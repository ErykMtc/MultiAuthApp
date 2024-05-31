package com.authapp.backend.controller;

import com.authapp.backend.model.Post;
import com.authapp.backend.model.User;
import com.authapp.backend.repository.PostRepository;
import com.authapp.backend.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/posts")
public class PostController {


    private PostRepository postRepository;

    private UserRepository userRepository;

    @Autowired
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public List<Post> getAllPosts(){
        return postRepository.findAllWithUser();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPost(@Valid @RequestBody Post post, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid post data: " + bindingResult.getAllErrors());
        } else {
            Integer userId = post.getUserId(); // Pobierz userId z pola userId w obiekcie Post

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User ID cannot be null");
            }

            // Sprawdź czy użytkownik istnieje
            boolean userExists = userRepository.existsById(userId);
            if (!userExists) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist");
            }

            // Utwórz obiekt użytkownika na podstawie userId
            User user = new User();
            user.setId(userId);
            post.setUser(user);

            // Zapisz post
            postRepository.save(post);
            return ResponseEntity.ok("Post added successfully");
        }
    }
}
