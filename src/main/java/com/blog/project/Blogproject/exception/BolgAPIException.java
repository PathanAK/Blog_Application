package com.blog.project.Blogproject.exception;

import org.springframework.http.HttpStatus;

public class BolgAPIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public BolgAPIException(HttpStatus httpStatus, String string) {
        this.httpStatus = httpStatus;
        this.message = string;
    }

    public BolgAPIException(String message, HttpStatus httpStatus, String string) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = string;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }
}
