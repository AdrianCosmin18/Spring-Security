package com.example.springsecutiryexemple.security;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.springsecutiryexemple.security.UserPermission.*;

@AllArgsConstructor
public enum UserRole {

    USER(Sets.newHashSet(BOOK_READ, MANAGE_BOOK_LIST)),
    ADMIN(Sets.newHashSet(BOOK_ADD, BOOK_WRITE, BOOK_READ, USER_ADD, USER_WRITE, USER_READ));

    private final Set<UserPermission> permissions;
    public Set<UserPermission> getPermissions(){ return permissions; }

    //implementez rolul in spring security luand permisiunile generate
    //acel set de permisiuni specifice unui user => ROLE_USER
    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){

        Set<SimpleGrantedAuthority> collect = getPermissions()
                .stream()
                .map(e -> new SimpleGrantedAuthority(e.getPermission()))
                .collect(Collectors.toSet());

        collect.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return collect;
    }


}
