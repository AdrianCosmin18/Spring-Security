package com.example.springsecutiryexemple.service;

import com.example.springsecutiryexemple.DTO.BookDto;
import com.example.springsecutiryexemple.DTO.exceptions.BookExistsException;
import com.example.springsecutiryexemple.DTO.exceptions.NoBookFoundException;
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

    public List<Book> getAvailableBooks(){//carti pe care nu le are nimeni

        return this.bookRepo.findAllAvailableBooks();
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

    public Book getBookById(Long id){
        return this.bookRepo.findById(id)
                .orElseThrow(() -> new NoBookFoundException("There is no book with this id: "+ id));
    }

    public void updateBook(long bookId, BookDto bookDto){

        Book book = this.bookRepo.findById(bookId)
                .orElseThrow(() -> new NoBookFoundException("No book found with this id: " + bookId));

        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setNumberOfPages(bookDto.getNumberOfPages());

        this.bookRepo.saveAndFlush(book);
    }

    public void deleteBook(long id){
        if(this.bookRepo.findById(id).isPresent()){
            this.bookRepo.deleteById(id);
        }else{
            throw new NoBookFoundException("No book found with this id: " + id);
        }
    }


}
