package com.example.backendBasicAuth.controller;

import com.example.backendBasicAuth.model.Post;
import com.example.backendBasicAuth.model.PostView;
import com.example.backendBasicAuth.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public List<PostView> getAllPosts(){
        return postService.getAllPost();
    }

    @PostMapping("/add")
    public ResponseEntity<String> addPost(@RequestBody Post post, BindingResult bindingResult) {
        String result = postService.addPost(post, bindingResult);

        if (result.startsWith("Invalid") || result.startsWith("User")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id){
        String result = postService.deletePost(id);

        if (result.startsWith("Post")){
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
