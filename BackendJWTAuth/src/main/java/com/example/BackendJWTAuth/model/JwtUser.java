package com.example.BackendJWTAuth.model;

public class JwtUser {
        private Integer id;
        private User.Role role;
        private String token;
        private String refreshToken;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public User.Role getRole() {
            return role;
        }

        public void setRole(User.Role role) {
            this.role = role;
        }


        public void copyFrom(User user){
            this.id = user.getId();
            this.role = user.getRole();
        }
}
