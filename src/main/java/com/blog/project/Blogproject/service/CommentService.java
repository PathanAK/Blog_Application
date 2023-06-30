package com.blog.project.Blogproject.service;

import com.blog.project.Blogproject.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long post_id, CommentDto commentDto);

    List<CommentDto> getAllCommentsByPosId(Long post_id);

    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto request);

    String deleteComment(Long postId, Long commentId);

}
