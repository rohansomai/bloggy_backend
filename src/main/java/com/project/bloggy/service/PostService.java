package com.project.bloggy.service;

import com.project.bloggy.dto.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Long userId, Long labelId);

    PostDTO updatePost(PostDTO postDTO, Long postId);

    void deletePost(Long postId);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long postId);

    List<PostDTO> getPostsByLabelId(Long labelId);

    List<PostDTO> getPostsByUserId(Long userId);

    List<PostDTO> searchPosts(String keyword);

}
