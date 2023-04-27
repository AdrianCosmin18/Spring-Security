package com.example.springsecutiryexemple.service;

import com.example.springsecutiryexemple.DTO.BookDto;
import com.example.springsecutiryexemple.exceptions.BookExistsException;
import com.example.springsecutiryexemple.exceptions.NoBookFoundException;
import com.example.springsecutiryexemple.models.Book;
import com.example.springsecutiryexemple.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepo bookRepo;


    public List<Book> getAll(){
        List<Book> books = this.bookRepo.findAll();
        if(books.isEmpty()){
            throw new NoBookFoundException("No book found");
        }
        return books;
    }

    @Transactional
    @Modifying
    public void addBook(BookDto bookDTO){

        if(this.bookRepo.findBookByName(bookDTO.getName()).isPresent()){
            throw new BookExistsException("The book already exists");
        }
        this.bookRepo.save(new Book(bookDTO.getName(), bookDTO.getAuthor(), bookDTO.getNumberOfPages()));
    }

    public Book getBookByTitle(String title){
        return this.bookRepo.findBookByName(title)
                .orElseThrow(() -> new NoBookFoundException("The book does not exists"));
    }


}