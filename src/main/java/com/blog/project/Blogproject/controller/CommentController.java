package com.blog.project.Blogproject.controller;

import com.blog.project.Blogproject.payload.CommentDto;
import com.blog.project.Blogproject.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    @Autowired
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{post_id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long post_id,
                                                    @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(post_id, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{post_id}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsById(@PathVariable Long post_id) {
        return new ResponseEntity<>(commentService.getAllCommentsByPosId(post_id), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable Long postId,
                                                     @PathVariable Long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long postId,
                                                    @PathVariable Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto) {
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId) {
        String deletionMessage = commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>(deletionMessage, HttpStatus.NO_CONTENT);
    }
}
