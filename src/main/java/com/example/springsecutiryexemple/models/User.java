package com.example.springsecutiryexemple.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "user")
public class User {

    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "phone number is required")
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;


    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER,
            mappedBy = "user")
    @JsonManagedReference
    private List<Book> books;

    public void addBook(Book b){
        this.books.add(b);
        b.setUser(this);
    }

    public void removeBook(Book b){
        this.books.remove(b);
    }

}
