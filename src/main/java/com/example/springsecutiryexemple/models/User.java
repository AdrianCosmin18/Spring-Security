package com.example.springsecutiryexemple.models;

import com.example.springsecutiryexemple.security.UserRole;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "User")
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @NotEmpty
    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "phone number is required")
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    public User(String name, String password, String email, String phone) {
        this.name = name;
        this.password = new BCryptPasswordEncoder().encode(password);
        this.email = email;
        this.phone = phone;
    }

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //ii dam rolul de user creat in UserRole pe baza lui UserPermission
        return UserRole.USER.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
