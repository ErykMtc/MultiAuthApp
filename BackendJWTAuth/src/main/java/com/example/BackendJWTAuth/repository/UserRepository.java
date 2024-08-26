package com.example.BackendJWTAuth.repository;

import com.example.BackendJWTAuth.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.name = :name")
    boolean existsByName(@Param("name") String name);
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId")
    boolean existsById(@Param("userId") Integer userId);
    @Query("SELECT u FROM User u WHERE u.name = :name")
    Optional<User> foundUser(@Param("name") String name);
    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.name = :name")
    void deleteByName(@Param("name") String name);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId")
    void updatePassword(@Param("userId") Integer userId, @Param("password") String password);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name WHERE u.id = :userId")
    void updateName(@Param("userId") Integer userId, @Param("name") String name);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :userId")
    void updateRole(@Param("role") Integer userId, User.Role role);
}
