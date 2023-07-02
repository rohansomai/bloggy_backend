package com.project.bloggy.controller;

import com.project.bloggy.dto.ApiResponse;
import com.project.bloggy.dto.PostDTO;
import com.project.bloggy.dto.PostResponse;
import com.project.bloggy.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<ApiResponse> getPostByUser(@PathVariable Long userId) {
        List<PostDTO> postsByUser = this.postService.getPostsByUserId(userId);
        return new ResponseEntity<>(new ApiResponse("Posts list fetched successfully", postsByUser), HttpStatus.OK);
    }

    @GetMapping("/labels/{labelId}/posts")
    public ResponseEntity<ApiResponse> getPostByLabel(@PathVariable Long labelId) {
        List<PostDTO> postsByLabel = this.postService.getPostsByLabelId(labelId);
        return new ResponseEntity<>(new ApiResponse("Posts list fetched successfully", postsByLabel), HttpStatus.OK);
    }

    @PutMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> updatePost(@PathVariable Long postId, @RequestBody PostDTO postDTO) {
        PostDTO updatedPost = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<>(new ApiResponse("Post updated successfully", updatedPost), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long postId) {
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> getPostById(@PathVariable Long postId) {
        PostDTO postDTO = this.postService.getPostById(postId);
        return new ResponseEntity<>(new ApiResponse("Post fetched successfully", postDTO), HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse> getAllPosts(@RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                                                   @RequestParam(required = false, defaultValue = "postId") String sortBy,
                                                   @RequestParam(required = false, defaultValue = "asc") String sortType) {
        PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortType);
        return new ResponseEntity<>(new ApiResponse("Posts list fetched successfully", postResponse), HttpStatus.OK);
    }

    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<ApiResponse> searchPosts(@PathVariable String keyword) {
        List<PostDTO> searchPostsResult = this.postService.searchPosts(keyword);
        return new ResponseEntity<>(new ApiResponse("Posts search results fetched successfully", searchPostsResult), HttpStatus.OK);
    }

}
