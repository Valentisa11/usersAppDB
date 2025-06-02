package com.example.user.service;


import com.example.user.dto.UserCreateDTO;
import com.example.user.dto.UserDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {

    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::toDTO);
    }

   /* public UserDTO createUser(UserCreateDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return toDTO(userRepository.save(user));
    } */

    public Optional<UserDTO> updateUser(Long id, UserCreateDTO dto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(dto.getName());
                    user.setEmail(dto.getEmail());
                    return toDTO(userRepository.save(user));
                });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public void createUser(String alice, String mail, int i) {
    }
    public UserDTO createUser(UserCreateDTO dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (!isValidEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Невалидный email");
        }

        return null;
    }

    private boolean isValidEmail(String email) {

        return false;
    }
}