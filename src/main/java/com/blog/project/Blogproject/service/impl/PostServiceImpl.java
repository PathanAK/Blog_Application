package com.blog.project.Blogproject.service.impl;

import com.blog.project.Blogproject.entity.Post;
import com.blog.project.Blogproject.exception.ResourceNotFoundException;
import com.blog.project.Blogproject.payload.PostDto;
import com.blog.project.Blogproject.respository.PostRepository;
import com.blog.project.Blogproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        //Convert Dto to entity
        Post post = DtoToEntity(postDto);
        Post newPost = postRepository.save(post);
        //Convert entity to post
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        //list of all the posts
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto required, Long id) {

        Post existingPost = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        if(required.getTitle() != null) {
            existingPost.setTitle(required.getTitle());
        }
        if(required.getDescription() != null) {
            existingPost.setDescription(required.getDescription());
        }
        if(required.getContent() != null) {
            existingPost.setContent(required.getContent());
        }
        Post updatedPost = postRepository.save(existingPost);
        return mapToDto(updatedPost);
    }

    @Override
    public String deleteById(Long id) {
        Post post = postRepository.findById(id) .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found with id: " + id));

        postRepository.delete(post);
        return "Post deleted with " + id + " ..!";
    }

    //Convert entity to Dto
    private PostDto mapToDto(Post post) {

        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post DtoToEntity(PostDto postDto) {

        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
