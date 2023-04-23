package com.example.springsecutiryexemple.DTO;

public class LoginResponse {

    public Long userId;
    public String email;
    public String token;

    public LoginResponse(Long userId, String email, String token) {
        this.userId = userId;
        this.email = email;
        this.token = token;
    }
}
