package com.example.BackendJWTAuth.utils;


import com.example.BackendJWTAuth.model.Post;
import com.example.BackendJWTAuth.model.PostView;

public class PostMapper {
    public static PostView toPostView(Post post) {
        PostView postView = new PostView();
        postView.setId(post.getId());
        postView.setContent(post.getContent());
        postView.setTitle(post.getTitle());
        postView.setUserId(post.getUser().getId());
        postView.setUserName(post.getUser().getName());
        return postView;
    }
}
