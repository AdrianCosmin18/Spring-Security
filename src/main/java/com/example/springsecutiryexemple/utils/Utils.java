package com.example.springsecutiryexemple.utils;

public class Utils {

    public static final String MY_CODE = "Cosmin";
    public static final String  ADMINISTRATION = "Spring Security App";
    public static final String AUTHORITIES = "authorities";
    public static final long EXPIRATION_TIME = 432_000_000; // 5 days expressed in milliseconds
    public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String FORBIDDEN_MESSAGE = "You need to log in to access this page";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page";
    public static final String[] PUBLIC_URLS = {
            "/user/login",
            "/user/register",
            "/user/make-user-as-admin/*",
            "/user/add-book-to-user/*",
            "/user/remove-book-from-user/*",
            "/user/get-user-books/*",
            "/api/books/available-books"
    };
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";







}
