package com.example.springsecutiryexemple.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermission {
    BOOK_READ("book:read"), //a vedea lista de carti
    BOOK_WRITE("book:write"), //a edita o carte(update/delete)
    BOOK_ADD("book:add"),
    USER_ADD("user:add"), //a adauga o carte
    USER_READ("user:read"),
    USER_WRITE("user:write");
    private String permission;
    public String getPermission(){ return permission; }
}
