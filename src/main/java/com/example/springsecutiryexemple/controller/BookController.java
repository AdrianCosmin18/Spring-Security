package com.example.springsecutiryexemple.controller;

import com.example.springsecutiryexemple.DTO.BookDto;
import com.example.springsecutiryexemple.models.Book;
import com.example.springsecutiryexemple.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<Book>> getBooks(){
        return new ResponseEntity<>(this.bookService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public void addBook(@RequestBody BookDto bookDto){
        this.bookService.addBook(bookDto);
    }

    @GetMapping("/available-books")
    public ResponseEntity<List<Book>> getAvailableBooks(){
        return new ResponseEntity<>(this.bookService.getAvailableBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        return new ResponseEntity<>(this.bookService.getBookById(id), HttpStatus.OK);
    }
}
