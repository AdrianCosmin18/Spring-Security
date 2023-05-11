package com.example.springsecutiryexemple.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserPermission {
    BOOK_READ("book:read"), //a vedea lista de carti
    BOOK_WRITE("book:write"), //a edita o carte(update/delete),
    MANAGE_BOOK_LIST("book:add:remove"), // a adauga/returna o carte din lista sa de carti
    BOOK_ADD("book:add"),
    USER_ADD("user:add"), //a adauga un user
    USER_READ("user:read"),
    USER_WRITE("user:write");
    private String permission;
    public String getPermission(){ return permission; }
}
