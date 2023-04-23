package com.example.springsecutiryexemple.controller;

import com.example.springsecutiryexemple.DTO.LoginResponse;
import com.example.springsecutiryexemple.DTO.UserDTO;
import com.example.springsecutiryexemple.jwt.JWTTokenProvider;
import com.example.springsecutiryexemple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = {"/", "/user"})
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO user){

        authenticate(user.getEmail(), user.getPassword());
        return null;
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
