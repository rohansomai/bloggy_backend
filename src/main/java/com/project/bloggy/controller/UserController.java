package com.project.bloggy.controller;

import com.project.bloggy.dto.ApiResponse;
import com.project.bloggy.dto.UserDTO;
import com.project.bloggy.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserDTO createdUser = this.userService.createUser(userDTO);
        return new ResponseEntity<>(new ApiResponse("User created successfully", createdUser), HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long userId, @Valid @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = this.userService.updateUser(userDTO, userId);
        return new ResponseEntity<>(new ApiResponse("User updated successfully", updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted successfully", null), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable Long userId) {
        UserDTO user = this.userService.getUserById(userId);
        return new ResponseEntity<>(new ApiResponse("User fetched successfully", user), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAllUsersList() {
        List<UserDTO> usersList = this.userService.getAllUsersList();
        return new ResponseEntity<>(new ApiResponse("Users List fetched successfully", usersList), HttpStatus.OK);
    }

}
