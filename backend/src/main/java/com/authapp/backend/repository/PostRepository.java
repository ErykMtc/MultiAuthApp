package com.authapp.backend.repository;

import com.authapp.backend.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends ListCrudRepository<Post, Integer> {
    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    List<Post> findAllWithUser();
}
