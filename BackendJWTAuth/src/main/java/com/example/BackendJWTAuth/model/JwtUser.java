package com.example.BackendJWTAuth.model;

public class JwtUser {
        private Integer id;
        private User.Role role;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        private String token;

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
