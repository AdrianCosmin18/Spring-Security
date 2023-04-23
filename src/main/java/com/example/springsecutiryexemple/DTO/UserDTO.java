package com.example.springsecutiryexemple.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String password;
    private String email;
    private String phone;
}
