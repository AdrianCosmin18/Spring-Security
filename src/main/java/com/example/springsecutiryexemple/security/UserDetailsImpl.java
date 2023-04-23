package com.example.springsecutiryexemple.security;

import com.example.springsecutiryexemple.exceptions.UserNotFoundException;
import com.example.springsecutiryexemple.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsImpl implements UserDetailsService {
    //clasa default pentru service de useri, facem o implementare pentru a preciza ca vrem sa venim cu
    //userii nostrii din repoul nostru

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String s){

        return userRepo.getUserByEmail(s)
                .orElseThrow(() -> new UserNotFoundException("User " + s +" not found"));
    }
}