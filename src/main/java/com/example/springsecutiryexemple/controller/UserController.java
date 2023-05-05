package com.example.springsecutiryexemple.controller;

import com.example.springsecutiryexemple.DTO.LoginResponse;
import com.example.springsecutiryexemple.DTO.RegisterResponse;
import com.example.springsecutiryexemple.DTO.UserDTO;
import com.example.springsecutiryexemple.jwt.JWTTokenProvider;
import com.example.springsecutiryexemple.models.Book;
import com.example.springsecutiryexemple.models.User;
import com.example.springsecutiryexemple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.springsecutiryexemple.utils.Utils.JWT_TOKEN_HEADER;

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
        User userLogin = userService.findUserByEmail(user.getEmail());

        HttpHeaders jwtHeader = getJwtHeader(userLogin);
        Long userId = this.userService.findIdByUsername(user.getEmail());
        LoginResponse loginResponse = new LoginResponse(userId, user.getEmail(), jwtHeader.getFirst(JWT_TOKEN_HEADER));

        return new ResponseEntity<>(loginResponse, jwtHeader, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> addUser(@RequestBody UserDTO userDTO){
        this.userService.addUser(userDTO);
        User userLogin = this.userService.findUserByEmail(userDTO.getEmail());
        HttpHeaders jwtHeader = getJwtHeader(userLogin);
        long userId = this.userService.findIdByUsername(userDTO.getEmail());
        RegisterResponse response = new RegisterResponse(userId, userDTO.getEmail(), jwtHeader.getFirst(JWT_TOKEN_HEADER));

        //primul argument e body
        //al doilea e header
        //al treilea e statusul, e structura generala a lui ResponseEntity
        return new ResponseEntity<>(response, jwtHeader, HttpStatus.OK);
    }

    @PutMapping("/make-user-as-admin/{email}")
    public void makeUserAsAdmin(@PathVariable String email){
        this.userService.makeUserAsAdmin(email);
    }

    private void authenticate(String username, String password){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(User user) {
        HttpHeaders headers = new HttpHeaders();

        // punem in header nou token generat cu numele: Jwt-Token
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }

    @PostMapping("/add-book-to-user/{email}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void addBookToUser(@PathVariable String email, @RequestParam(value = "bookId") long bookId){
        this.userService.addBookToUser(email, bookId);
    }

    @DeleteMapping("/remove-book-from-user/{email}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public void removeBookFromUser(@PathVariable String email, @RequestParam(value = "bookId") long bookId){
        this.userService.removeBookFromUser(email, bookId);
    }

    @GetMapping("/get-user-books/{email}")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<List<Book>> getUserBooks(@PathVariable String email){
        return new ResponseEntity<>(this.userService.getUserBooks(email), HttpStatus.OK);
    }
}
