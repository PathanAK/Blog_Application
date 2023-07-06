package com.blog.project.Blogproject.service;

import com.blog.project.Blogproject.payload.LoginDto;
import com.blog.project.Blogproject.payload.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
