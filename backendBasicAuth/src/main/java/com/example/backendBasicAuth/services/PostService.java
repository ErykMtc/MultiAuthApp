package com.example.backendBasicAuth.services;

import com.example.backendBasicAuth.model.Post;
import com.example.backendBasicAuth.model.PostView;
import com.example.backendBasicAuth.model.User;
import com.example.backendBasicAuth.repository.PostRepository;
import com.example.backendBasicAuth.repository.UserRepository;
import com.example.backendBasicAuth.utils.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository){
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public List<PostView> getAllPost() {
        List<Post> posts = postRepository.findAllWithUser();
        return posts.stream().map(PostMapper::toPostView).collect(Collectors.toList());
    }

    public String addPost(Post post, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "Invalid post data: " + bindingResult.getAllErrors();
        } else {
            Integer userId = post.getUserId(); // Pobierz userId z pola userId w obiekcie Post

            if (userId == null) {
                return "User ID cannot be null";
            }

            // Sprawdź czy użytkownik istnieje
            boolean userExists = userRepository.existsById(userId);
            if (!userExists) {
                return "User does not exist";
            }

            // Utwórz obiekt użytkownika na podstawie userId
            User user = new User();
            user.setId(userId);
            post.setUser(user);

            // Zapisz post
            postRepository.save(post);
            return "Post added successfully";
        }
    }
}
