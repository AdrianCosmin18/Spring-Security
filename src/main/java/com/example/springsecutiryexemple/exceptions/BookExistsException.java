package com.example.springsecutiryexemple.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookExistsException extends RuntimeException{
    public BookExistsException(String msg){
        super(msg);
    }
}
