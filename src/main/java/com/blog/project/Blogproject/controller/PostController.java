package com.blog.project.Blogproject.controller;

import com.blog.project.Blogproject.payload.PostDto;
import com.blog.project.Blogproject.payload.PostResponse;
import com.blog.project.Blogproject.service.PostService;
import com.blog.project.Blogproject.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "CURD Rest APIs For Post Resource"
)
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post Rest Api",
            description = "Create Post Rest Api used to save post in database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status 201 CREATED"
    )
    @SecurityRequirement(name="Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Post Rest Api",
            description = "GEt Post Rest Api used to get all the posts from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value="pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false ) int pageNo,
            @RequestParam(value="pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false ) int pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir

    ) {
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }
    @Operation(
            summary = "Get Post by Id Rest Api",
            description = "GEt Post Rest Api used to get post id from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping(value = "/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(postService.getPostById(id), HttpStatus.OK);
    }

//    @GetMapping(value = "/api/posts/{id}", produces = "application/vnd.javaGuides.v2+json")
//    public ResponseEntity<PostDtoV2> getByIdV2(@PathVariable Long id) {
//        PostDto postDto = postService.getPostById(id);
//        PostDtoV2 postDtoV2 = new PostDtoV2();
//        postDtoV2.setId(postDto.getId());
//        postDtoV2.setTitle(postDto.getTitle());
//        postDtoV2.setDescription(postDto.getDescription());
//        postDtoV2.setContent(postDto.getContent());
//        postDtoV2.setComments(postDto.getComments());
//        postDtoV2.setCategoryId(postDto.getCategoryId());
//        List<String> tags = new ArrayList<>();
//        tags.add("Java");
//        tags.add("Spring_Boot");
//        tags.add("AWS");
//        postDtoV2.setTags(tags);
//        return new ResponseEntity<>(postDtoV2, HttpStatus.OK);
//    }


    @Operation(
            summary = "Update Post Rest Api",
            description = "Update Post Rest Api used to update any post by id in database"
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP Status 202 ACCEPTED"
    )
    @SecurityRequirement(name="Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Long id) {
        return new ResponseEntity<>(postService.updatePost(postDto, id), HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Delete Post Rest Api",
            description = "Update Post Rest Api used to delete any post by Id from database"
    )
    @ApiResponse(
            responseCode = "202",
            description = "HTTP Status 202 ACCEPTED"
    )
    @SecurityRequirement(name="Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        return new ResponseEntity<>(postService.deleteById(id),HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Get Posts by Category ID Rest Api",
            description = "Get Posts by Category ID Rest Api used to get all posts by Category ID from database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status 200 OK"
    )
    @GetMapping(value = "/api/v1/posts/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostByCategoryID(@PathVariable Long categoryId){
        return new ResponseEntity<>(postService.getPostByCategory(categoryId), HttpStatus.OK);
    }
}
