package com.blog.project.Blogproject.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PostDtoV2 {
    private Long id;

    @Schema(
            description = "Blog post title"
    )
    //title should be empty
    //title should be minimum of 2 characters
    @NotEmpty
    @Size(min = 2, message = "Post title show be more than 2 characters")
    private String title;
    //Post description should not null or empty
    //Post description must be at least 10 characters
    @Schema(
            description = "Blog post description"
    )
    @NotEmpty
    @Size(min = 10, message = "Post description must be at least 10 characters")
    private String description;

    @Schema(
            description = "Blog post content"
    )
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    @Schema(
            description = "Blog post category"
    )
    private Long categoryId;
    private List<String> tags;

}
