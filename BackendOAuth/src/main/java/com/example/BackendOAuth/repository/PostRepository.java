package com.example.BackendOAuth.repository;

import com.example.BackendOAuth.model.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostRepository extends ListCrudRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.user")
    List<Post> findAllWithUser();

    @Query("SELECT COUNT(p) > 0 FROM Post p WHERE p.id = :id")
    boolean existsById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Post p WHERE p.id = :id")
    void deletePost(@Param("id") Long id);
}
