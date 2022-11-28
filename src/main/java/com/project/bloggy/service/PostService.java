package com.project.bloggy.service;

import com.project.bloggy.dto.PostDTO;
import com.project.bloggy.entity.User;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO, Long userId, Long labelId);

    PostDTO updatePost(PostDTO postDTO, Long postId);

    void deletePost(Long postId);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long postId);

    List<PostDTO> getPostsByLabelId(Double categoryId);

    List<PostDTO> getPostsByUserId(User userId);

    List<PostDTO> searchPosts(String keyword);

}
