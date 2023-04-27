package com.example.springsecutiryexemple.service;

import com.example.springsecutiryexemple.DTO.UserDTO;
import com.example.springsecutiryexemple.exceptions.UserExistsException;
import com.example.springsecutiryexemple.exceptions.UserNotFoundException;
import com.example.springsecutiryexemple.models.User;
import com.example.springsecutiryexemple.repo.UserRepo;
import com.example.springsecutiryexemple.security.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void addUser(UserDTO userDTO){

        boolean existsUser = this.userRepo.getUserByEmail(userDTO.getEmail()).isPresent();
        if(existsUser){
            throw new UserExistsException("User already exists");
        }
        this.userRepo.saveAndFlush(new User(userDTO.getName(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getPhone()));
    }

    public User findUserByEmail(String email){
        return this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Long findIdByUsername(String email){
        return this.userRepo.findIdByUsername(email)
                .orElseThrow(() -> new UserNotFoundException("User id not found"));
    }

    public void makeUserAsAdmin(String email){
        User user = this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user with this email"));
        user.setUserRole(UserRole.ADMIN);
        this.userRepo.saveAndFlush(user);
    }
}
