package com.example.springsecutiryexemple.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermission {
    BOOK_READ("book:read"), //a vedea lista de carti
    BOOK_WRITE("book:write"); //a edita o carte(update/delete)

    private String permission;
    public String getPermission(){ return permission; }
}
