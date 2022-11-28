package com.project.bloggy.service;

import com.project.bloggy.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO updateUser(UserDTO user, Long userId);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsersList();

    void deleteUser(Long userId);
}
