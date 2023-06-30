package com.blog.project.Blogproject.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;
    @NotEmpty(message = "Name should not be null or empty..!!")
    private String name;
    @Email
    @NotEmpty(message = "Email should not be null or empty..!!")
    private String email;
    @NotEmpty
    @Size(min = 10, message = "Body should minimum of 10 Characters..!!" )
    private String body;

}
