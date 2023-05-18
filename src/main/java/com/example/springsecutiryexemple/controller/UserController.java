package com.example.springsecutiryexemple.controller;

import com.example.springsecutiryexemple.DTO.BookDto;
import com.example.springsecutiryexemple.DTO.LoginResponse;
import com.example.springsecutiryexemple.DTO.RegisterResponse;
import com.example.springsecutiryexemple.DTO.UserDTO;
import com.example.springsecutiryexemple.jwt.JWTTokenProvider;
import com.example.springsecutiryexemple.models.Book;
import com.example.springsecutiryexemple.models.User;
import com.example.springsecutiryexemple.service.BookService;
import com.example.springsecutiryexemple.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static com.example.springsecutiryexemple.utils.Utils.JWT_TOKEN_HEADER;

@RestController
@CrossOrigin
@RequestMapping(path = {"/", "/user"})
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;



    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO user){

        authenticate(user.getEmail(), user.getPassword());
        User userLogin = userService.findUserByEmail(user.getEmail());

        Collection<? extends GrantedAuthority> authorities = userLogin.getAuthorities();

        HttpHeaders jwtHeader = getJwtHeader(userLogin);
        Long userId = this.userService.findIdByUsername(user.getEmail());
        LoginResponse loginResponse = new LoginResponse(userId, user.getEmail(), jwtHeader.getFirst(JWT_TOKEN_HEADER), authorities);

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
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void addBookToUser(@PathVariable String email, @RequestParam(value = "bookId") long bookId){
        this.userService.addBookToUser(email, bookId);
    }

    @DeleteMapping("/remove-book-from-user/{email}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public void removeBookFromUser(@PathVariable String email, @RequestParam(value = "bookId") long bookId){
        this.userService.removeBookFromUser(email, bookId);
    }

    @GetMapping("/get-user-books/{email}")
    @PreAuthorize("hasAuthority('book:read')")
    public ResponseEntity<List<Book>> getUserBooks(@PathVariable String email){
        return new ResponseEntity<>(this.userService.getUserBooks(email), HttpStatus.OK);
    }

    @PutMapping("/update-book/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void updateBook(@PathVariable long bookId, @RequestBody BookDto bookDto){
        this.bookService.updateBook(bookId, bookDto);
    }

    @DeleteMapping("/delete-book/{bookId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteBook(@PathVariable long bookId){
        this.bookService.deleteBook(bookId);
    }

    @GetMapping("/get-all-users")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<UserDTO> getAllUsers(){
        return this.userService.getAllUsers();
    }

    @GetMapping("/get-user-by-email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public UserDTO getUserByEmail(@PathVariable String email){
        return this.userService.getUserByEmail(email);
    }

    @DeleteMapping("/delete-user-by-email/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void deleteUser(@PathVariable String email){
        this.userService.deleteUser(email);
    }

    @PutMapping("/update-user/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void updateUser(@PathVariable String email, @RequestBody UserDTO userDTO){
        this.userService.updateUser(email, userDTO);
    }
}
