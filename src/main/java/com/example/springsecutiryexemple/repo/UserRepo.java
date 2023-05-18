package com.example.springsecutiryexemple.repo;

import com.example.springsecutiryexemple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

    @Query("select u.id from User u where u.email = ?1")
    Optional<Long> findIdByUsername(String email);

    @Modifying
    @Transactional
    @Query("delete from User u where u.email = ?1")
    void deleteUserByEmail(String email);
}
