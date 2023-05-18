package com.example.springsecutiryexemple.service;

import com.example.springsecutiryexemple.DTO.UserDTO;
import com.example.springsecutiryexemple.DTO.exceptions.NoBookFoundException;
import com.example.springsecutiryexemple.DTO.exceptions.UserExistsException;
import com.example.springsecutiryexemple.DTO.exceptions.UserNotFoundException;
import com.example.springsecutiryexemple.models.Book;
import com.example.springsecutiryexemple.models.User;
import com.example.springsecutiryexemple.repo.BookRepo;
import com.example.springsecutiryexemple.repo.UserRepo;
import com.example.springsecutiryexemple.security.UserRole;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookRepo bookRepo;

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

    public UserDTO getUserByEmail(String email){
        User user = this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        UserDTO userDTO = new UserDTO(user.getName(), user.getPassword(), user.getEmail(), user.getPhone());

        String role = user.getUserRole().name();
        if(role.equals("ADMIN")){
            userDTO.setRole("ADMIN");
        }else if(role.equals("USER")){
            userDTO.setRole("USER");
        }

        return userDTO;
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

    public void addBookToUser(String email, long bookId){
        User user = this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user with this email"));
        Book book = this.bookRepo.findById(bookId)
                .orElseThrow(() -> new NoBookFoundException("No book found with this id: " + bookId));

        user.addBook(book);
        this.userRepo.saveAndFlush(user);
        this.bookRepo.saveAndFlush(book);
    }

    public void removeBookFromUser(String email, long bookId){
        User user = this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user with this email"));
        Book book = this.bookRepo.findById(bookId)
                .orElseThrow(() -> new NoBookFoundException("No book found with this id: " + bookId));

        user.removeBook(book);
        book.setUser(null);
        this.userRepo.saveAndFlush(user);
        this.bookRepo.saveAndFlush(book);
    }

    public List<Book> getUserBooks(String email){
        User user = this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No user with this email"));
        return user.getBooks();
    }

    public List<UserDTO> getAllUsers(){

        List<User> users = this.userRepo.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User u: users){

            UserDTO userDTO = new UserDTO(u.getName(), u.getPassword(), u.getEmail(), u.getPhone());
            String role = u.getUserRole().name();
            if(role.equals("ADMIN")){
                userDTO.setRole("ADMIN");
            }else if(role.equals("USER")){
                userDTO.setRole("USER");
            }
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public void deleteUser(String email){
        User user = this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        this.userRepo.deleteUserByEmail(email);
    }

    public void updateUser(String email, UserDTO userDTO){
        User user = this.userRepo.getUserByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        this.userRepo.saveAndFlush(user);
    }
}
