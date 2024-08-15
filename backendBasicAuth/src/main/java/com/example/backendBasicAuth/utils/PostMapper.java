package com.example.backendBasicAuth.utils;

import com.example.backendBasicAuth.model.Post;
import com.example.backendBasicAuth.model.PostView;

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
