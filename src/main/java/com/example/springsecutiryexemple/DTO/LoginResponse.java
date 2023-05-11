package com.example.springsecutiryexemple.DTO;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class LoginResponse {

    public Long userId;
    public String email;
    public String token;
    public Collection<? extends GrantedAuthority> authorities;

    public LoginResponse(Long userId, String email, String token) {
        this.userId = userId;
        this.email = email;
        this.token = token;
    }

    public LoginResponse(Long userId, String email, String token, Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.email = email;
        this.token = token;
        this.authorities = authorities;
    }
}
