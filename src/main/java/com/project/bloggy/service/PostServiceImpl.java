package com.project.bloggy.service;

import com.project.bloggy.dto.PostDTO;
import com.project.bloggy.entity.Label;
import com.project.bloggy.entity.Post;
import com.project.bloggy.entity.User;
import com.project.bloggy.exception.ResourceNotFoundException;
import com.project.bloggy.repository.LabelRepository;
import com.project.bloggy.repository.PostRepository;
import com.project.bloggy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    public PostServiceImpl(PostRepository postRepository, LabelRepository labelRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.labelRepository = labelRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    private final PostRepository postRepository;
    private final LabelRepository labelRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Long userId, Long labelId) {
        Post post = this.dtoToPost(postDTO);
        Label label = this.labelRepository.findById(labelId)
                .orElseThrow(() -> new ResourceNotFoundException("Label", "labelId", labelId));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        post.setUser(user);
        post.setLabel(label);
        Post savedPost = this.postRepository.save(post);
        PostDTO savedPostDTO = this.postToDto(savedPost);
        savedPostDTO.setAddedDate(savedPost.getCreatedTimestamp());
        return savedPostDTO;
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Long postId) {
        Post currentPost = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        currentPost.setTitle(postDTO.getTitle());
        currentPost.setContent(postDTO.getContent());
        currentPost.setLocation(postDTO.getLocation());
        return this.postToDto(this.postRepository.save(currentPost));
    }

    @Override
    public void deletePost(Long postId) {
        Post currentPost = this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
        this.postRepository.delete(currentPost);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> allPostsList = this.postRepository.findAll();
        return allPostsList.stream().map(this::postToDto).collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long postId) {
        return this.postToDto(this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId)));
    }

    @Override
    public List<PostDTO> getPostsByLabelId(Double labelId) {
//        this.postRepository.findByLabel()
        return null;
    }

    @Override
    public List<PostDTO> getPostsByUserId(User userId) {
        return null;
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        return null;
    }

    private Post dtoToPost(PostDTO postDTO) {
        return this.modelMapper.map(postDTO, Post.class);
    }

    private PostDTO postToDto(Post post) {
        return this.modelMapper.map(post, PostDTO.class);
    }
}
