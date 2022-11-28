package com.project.bloggy.controller;

import com.project.bloggy.dto.ApiResponse;
import com.project.bloggy.dto.PostDTO;
import com.project.bloggy.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {

    public PostController(PostService postService) {
        this.postService = postService;
    }

    private final PostService postService;

    @PostMapping("/users/{userId}/labels/{labelId}/posts")
    public ResponseEntity<ApiResponse> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Long userId, @PathVariable Long labelId) {
        PostDTO postDto = this.postService.createPost(postDTO, userId, labelId);
        return new ResponseEntity<>(new ApiResponse("Post created successfully", postDto), HttpStatus.CREATED);
    }
}
