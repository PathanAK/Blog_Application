package com.blog.project.Blogproject.payload;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {

    private Long id;

    //title should be empty
    //title should be minimum of 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title show be more than 2 characters")
    private String title;
    //Post description should not null or empty
    //Post description must be at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Post description must be at least 10 characters")
    private String description;

    @NotEmpty
    private String content;
    private Set<CommentDto> comments;
}
