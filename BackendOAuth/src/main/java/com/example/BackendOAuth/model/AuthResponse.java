package com.example.BackendOAuth.model;

public class AuthResponse {
    private String login;
    private String pwd;
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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
    }
}
