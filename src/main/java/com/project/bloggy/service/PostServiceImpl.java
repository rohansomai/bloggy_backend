package com.project.bloggy.service;

import com.project.bloggy.dto.PostDTO;
import com.project.bloggy.dto.PostResponse;
import com.project.bloggy.entity.Label;
import com.project.bloggy.entity.Post;
import com.project.bloggy.entity.User;
import com.project.bloggy.exception.ResourceNotFoundException;
import com.project.bloggy.repository.LabelRepository;
import com.project.bloggy.repository.PostRepository;
import com.project.bloggy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        return this.postToDto(savedPost);
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
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortType) {
        Sort sort;
        if (sortType.equalsIgnoreCase("asc")) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> postsByPage = this.postRepository.findAll(pageable);
        List<Post> postsList = postsByPage.getContent();
        PostResponse postResponse = new PostResponse();
        postResponse.setPostsList(postsList.stream().map(this::postToDto).collect(Collectors.toList()));
        postResponse.setLastPage(postsByPage.isLast());
        postResponse.setPageNumber(postsByPage.getNumber());
        postResponse.setPageSize(postsByPage.getSize());
        postResponse.setTotalElements(postsByPage.getTotalElements());
        postResponse.setTotalPages(postsByPage.getTotalPages());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(Long postId) {
        return this.postToDto(this.postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId)));
    }

    @Override
    public List<PostDTO> getPostsByLabelId(Long labelId) {
        Label label = this.labelRepository.findById(labelId).orElseThrow(() -> new ResourceNotFoundException("Label", "labelId", labelId));
        return this.postRepository.findByLabel(label).stream().map(this::postToDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getPostsByUserId(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));
        return this.postRepository.findByUser(user).stream().map(this::postToDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        return this.postRepository.findByTitleContainingIgnoreCase(keyword).stream().map(this::postToDto).collect(Collectors.toList());
    }

    private Post dtoToPost(PostDTO postDTO) {
        return this.modelMapper.map(postDTO, Post.class);
    }

    private PostDTO postToDto(Post post) {
        return this.modelMapper.map(post, PostDTO.class);
    }
}
