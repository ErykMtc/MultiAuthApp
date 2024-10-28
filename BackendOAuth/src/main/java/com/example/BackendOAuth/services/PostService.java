package com.example.BackendOAuth.services;

import com.example.BackendOAuth.model.Post;
import com.example.BackendOAuth.model.PostView;
import com.example.BackendOAuth.model.User;
import com.example.BackendOAuth.repository.PostRepository;
import com.example.BackendOAuth.repository.UserRepository;
import com.example.BackendOAuth.utils.PostMapper;
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
            Long userId = post.getUserId();

            if (userId == null) {
                return "User ID cannot be null";
            }

            boolean userExists = userRepository.existsById(userId);
            if (!userExists) {
                return "User does not exist";
            }

            User user = new User();
            user.setId(userId);
            post.setUser(user);

            postRepository.save(post);
            return "Post added successfully";
        }
    }

    public String deletePost(Long id){
        if(!postRepository.existsById(id)){
            return "Post does not exist";
        }
        postRepository.deletePost(id);
        return "Deleted successfully";
    }
}
