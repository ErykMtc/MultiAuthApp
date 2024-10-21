package com.example.backendBasicAuth.model;

public class AuthResponse {
    private Integer userId;
    private String login;
    private String pwd;
    private User.Role role;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void copyFrom(User user){
        this.login = user.getName();
        this.pwd = user.getPassword();
        this.role = user.getRole();
        this.userId = user.getId();
    }
}
