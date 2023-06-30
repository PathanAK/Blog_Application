package com.blog.project.Blogproject.service.impl;

import com.blog.project.Blogproject.entity.Comment;
import com.blog.project.Blogproject.entity.Post;
import com.blog.project.Blogproject.exception.BolgAPIException;
import com.blog.project.Blogproject.exception.ResourceNotFoundException;
import com.blog.project.Blogproject.payload.CommentDto;
import com.blog.project.Blogproject.respository.CommentRepository;
import com.blog.project.Blogproject.respository.PostRepository;
import com.blog.project.Blogproject.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto createComment(Long post_id, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        //Retrieve post by id

        Post post = postRepository.findById(post_id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", post_id));

        //Set post to comment entity
        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getAllCommentsByPosId(Long post_id) {

        List<Comment> comments = commentRepository.findByPostId(post_id);

        return comments.stream().map((comment) -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BolgAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto request) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        Comment exitingComment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!exitingComment.getPost().getId().equals(post.getId())) {
            throw new BolgAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        if (request.getName() != null) {
            exitingComment.setName(request.getName());
        }
        if (request.getEmail() != null) {
            exitingComment.setEmail(request.getEmail());
        }
        if (request.getBody() != null) {
            exitingComment.setBody(request.getBody());
        }

        Comment updatedComment = commentRepository.save(exitingComment);

        return mapToDto(updatedComment);
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BolgAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        commentRepository.deleteById(commentId);
        return "Comment deleted with comment_id number" + commentId ;

    }


    private CommentDto mapToDto(Comment comment) {

        CommentDto commentDto = mapper.map(comment, CommentDto.class);

        return commentDto;
    }


    private Comment mapToEntity(CommentDto commentDto) {

        Comment comment = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());

        return comment;
    }
}
