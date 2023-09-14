package com.example.finances.services;

import com.example.finances.DTO.UserDTO;
import com.example.finances.exceptions.DuplicateResourceException;
import com.example.finances.exceptions.ResourceNotFoundException;
import com.example.finances.DTO.mappers.UserDTOMapper;
import com.example.finances.entities.User;
import com.example.finances.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserDTOMapper userDTOMapper) {
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getUser(Long userId) {
        return userRepository.findById(userId)
                .map(userDTOMapper)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "user with id [%s] not found".formatted(userId)
                ));
    }

    public UserDTO createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("username \"%s\" taken".formatted(user.getUsername()));
        }

        if (!user.getUsername().trim().isEmpty()
                && !user.getPassword().trim().isEmpty()) {

            User createdUser =
                    new User(
                            user.getUsername(),
                            user.getPassword()
                    );

            userRepository.save(createdUser);
            return userDTOMapper.apply(createdUser);
        }
        throw new IllegalStateException("username or password invalid");
    }

    public void updateUser(String username, String password, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "user with id [%s] not found".formatted(userId)
                ));

        if(username != null) {
            if (userRepository.existsByUsername(username)) {
                throw new DuplicateResourceException("username \"%s\" taken".formatted(username));
            }
            user.setUsername(username);
        }
        if(password != null) {
            user.setPassword(password);
        }
        userRepository.save(user);
    }
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}