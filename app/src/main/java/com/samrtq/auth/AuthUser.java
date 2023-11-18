package com.samrtq.auth;

public class AuthUser {
    private String email;
    private String password;

    public AuthUser() {

    }

    public String getEmail() {
        return email;
    }

    public AuthUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public AuthUser setPassword(String password) {
        this.password = password;
        return this;
    }
}
