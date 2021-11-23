package com.kusitms.kusitmsmarket.request;

public class LoginRequest {
    private String password;
    private String username;

    public LoginRequest(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
