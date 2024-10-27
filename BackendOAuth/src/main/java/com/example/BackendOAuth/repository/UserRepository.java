package com.example.BackendOAuth.repository;


import com.example.BackendOAuth.model.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username")
    boolean existsByUserName(@Param("username") String username);
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);
    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.id = :userId")
    boolean existsById(@Param("userId") Long userId);
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);
    @Query("SELECT u FROM User u WHERE u.refreshToken = :refreshToken")
    Optional<User> findByRefreshToken(@Param("refreshToken") String refreshToken);

    @Modifying
    @Query("UPDATE User u SET u.refreshToken = :refreshToken WHERE u.username = :username")
    void updateRefreshToken(@Param("username") String username, @Param("refreshToken") String refreshToken);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.name = :name")
    void deleteByName(@Param("name") String name);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password = :password WHERE u.id = :userId")
    void updatePassword(@Param("userId") Long userId, @Param("password") String password);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name = :name WHERE u.id = :userId")
    void updateName(@Param("userId") Long userId, @Param("name") String name);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.role = :role WHERE u.id = :userId")
    void updateRole(@Param("userId") Long userId, @Param("role") String role);
}
