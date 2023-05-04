package com.example.springsecutiryexemple.repo;

import com.example.springsecutiryexemple.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {


    Optional<Book> findBookByName(String name);

    @Query("select b from Book b where b.user is null")
    List<Book> findAllAvailableBooks();
}
