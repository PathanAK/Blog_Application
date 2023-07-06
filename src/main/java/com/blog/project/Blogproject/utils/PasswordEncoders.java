package com.blog.project.Blogproject.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoders {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoders = new BCryptPasswordEncoder();
        System.out.println(passwordEncoders.encode("asif"));
        System.out.println(passwordEncoders.encode("sai"));
    }
}
