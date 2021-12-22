package com.example.estate.model;

public class User {
    private final String password;
    private final String role;


    public User(String password, String role) {
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

}
