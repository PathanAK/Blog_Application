package com.blog.project.Blogproject.service;

import com.blog.project.Blogproject.payload.PostDto;
import com.blog.project.Blogproject.payload.PostResponse;

import java.util.List;

public interface PostService {
    //to create a new post in the database
    PostDto createPost(PostDto postDto);
    //to get all the posts from the database
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    //get post by ID
    PostDto getPostById(Long id);
    //update post by ID
    PostDto updatePost(PostDto postDto, Long id);
    //Delete post by id
    String deleteById(Long id);
    //get post by categoryId
    List<PostDto> getPostByCategory(Long categoryID);


}
