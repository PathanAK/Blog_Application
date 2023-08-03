package com.blog.project.Blogproject.service.impl;

import com.blog.project.Blogproject.entity.Category;
import com.blog.project.Blogproject.entity.Post;
import com.blog.project.Blogproject.exception.ResourceNotFoundException;
import com.blog.project.Blogproject.payload.PostDto;
import com.blog.project.Blogproject.payload.PostResponse;
import com.blog.project.Blogproject.respository.CategoryRepository;
import com.blog.project.Blogproject.respository.PostRepository;
import com.blog.project.Blogproject.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    private ModelMapper mapper;

    private CategoryRepository categoryRepository;

    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper mapper,
                           CategoryRepository categoryRepository) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("Category","id", postDto.getId()));

        //Convert Dto to entity
        Post post = DtoToEntity(postDto);
        post.setCategory(category);
        Post newPost = postRepository.save(post);
        //Convert entity to post
        PostDto postResponse = mapToDto(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        //list of all the posts
        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> listOfPages = posts.getContent();

        List<PostDto> content =  listOfPages.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {

        Post post = postRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Post","id",id));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto required, Long id) {

        Post existingPost = postRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Post","id",id));

        Category category = categoryRepository.findById(required.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", required.getCategoryId()));

        if(required.getTitle() != null) {
            existingPost.setTitle(required.getTitle());
        }
        if(required.getDescription() != null) {
            existingPost.setDescription(required.getDescription());
        }
        if(required.getContent() != null) {
            existingPost.setContent(required.getContent());
        }
        if(required.getCategoryId()!=null) {
            existingPost.setCategory(category);
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

    @Override
    public List<PostDto> getPostByCategory(Long categoryID) {

        Category category = categoryRepository.findById(categoryID)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryID));

        List<Post> posts = postRepository.findByCategoryId(categoryID);

        return posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
    }

    //Convert entity to Dto
    private PostDto mapToDto(Post post) {

        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDto = new PostDto();
//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post DtoToEntity(PostDto postDto) {

        Post post = mapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setId(postDto.getId());
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
}
