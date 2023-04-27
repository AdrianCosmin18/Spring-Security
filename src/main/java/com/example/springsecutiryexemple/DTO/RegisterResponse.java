package com.example.springsecutiryexemple.DTO;

public class RegisterResponse {

    public Long userId;
    public String email;
    public String token;

    public RegisterResponse(Long userId, String email, String token) {
        this.userId = userId;
        this.email = email;
        this.token = token;
    }
}
