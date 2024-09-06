package com.example.BackendOAuth.utils;


import com.example.BackendOAuth.model.Post;
import com.example.BackendOAuth.model.PostView;

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