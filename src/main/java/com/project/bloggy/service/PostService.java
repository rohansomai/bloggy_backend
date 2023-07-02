package com.project.bloggy.service;

import com.project.bloggy.dto.PostDTO;
import com.project.bloggy.dto.PostResponse;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Long userId, Long labelId);

    PostDTO updatePost(PostDTO postDTO, Long postId);

    void deletePost(Long postId);

    PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortType);

    PostDTO getPostById(Long postId);

    List<PostDTO> getPostsByLabelId(Long labelId);

    List<PostDTO> getPostsByUserId(Long userId);

    List<PostDTO> searchPosts(String keyword);

}
