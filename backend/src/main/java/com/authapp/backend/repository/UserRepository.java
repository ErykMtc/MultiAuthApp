package com.authapp.backend.repository;

import com.authapp.backend.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.name = :name")
    boolean existsByName(@Param("name") String name);
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId")
    boolean existsById(@Param("userId") Integer userId);
}
