package com.example.BackendOAuth.controller;

import com.example.BackendOAuth.model.Post;
import com.example.BackendOAuth.model.PostView;
import com.example.BackendOAuth.services.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.BackendOAuth.controller.UserController.BEARER_KEY_SECURITY_SCHEME;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @GetMapping("/")
    public List<PostView> getAllPosts(){
        return postService.getAllPost();
    }

    @CrossOrigin(origins = "*")
    @Operation(security = {@SecurityRequirement(name = BEARER_KEY_SECURITY_SCHEME)})
    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestBody Post post, BindingResult bindingResult) {
        String result = postService.addPost(post, bindingResult);

        if (result.startsWith("Invalid") || result.startsWith("User")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.ok(result);
    }
}
