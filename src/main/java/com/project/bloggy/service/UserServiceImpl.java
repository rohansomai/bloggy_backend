package com.project.bloggy.service;

import com.project.bloggy.dto.UserDTO;
import com.project.bloggy.entity.User;
import com.project.bloggy.exception.ResourceNotFoundException;
import com.project.bloggy.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = this.dtoToUser(userDTO);
        return this.userToDto(this.userRepository.save(user));
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Long userId) {
        User currentUser = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        currentUser.setBio(userDTO.getBio());
        currentUser.setEmail(userDTO.getEmail());
        currentUser.setName(userDTO.getName());
        currentUser.setPassword(userDTO.getPassword());
        return this.userToDto(this.userRepository.save(currentUser));
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return this.userToDto(this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId)));
    }

    @Override
    public List<UserDTO> getAllUsersList() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepository.delete(user);
    }

    private User dtoToUser(UserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
//        return new User(userDTO.getUserId(), userDTO.getEmail(),
//                userDTO.getName(), userDTO.getPassword(), userDTO.getBio());
    }

    private UserDTO userToDto(User user) {
        return this.modelMapper.map(user, UserDTO.class);
//        return new UserDTO(user.getUserId(), user.getEmail(),
//                user.getName(), user.getPassword(), user.getBio());
    }
}
