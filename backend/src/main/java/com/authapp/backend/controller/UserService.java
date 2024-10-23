package com.authapp.backend.controller;

import com.authapp.backend.model.User;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public List<User> unSecFindUserByName(String name) {
        List<User> users = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/blog", "postgres", "password");

            String query = "SELECT * FROM users WHERE name = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(User.Role.USER);
                users.add(user);
            }

            resultSet.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }
}