package com.example.springsecutiryexemple.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

@Data
@AllArgsConstructor
public class BookDto {

    private String name;
    private String author;
    private int numberOfPages;
}
