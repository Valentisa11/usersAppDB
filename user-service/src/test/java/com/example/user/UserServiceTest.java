package com.example.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.user.dto.UserCreateDTO;
import com.example.user.dto.UserDTO;
import com.example.user.model.User;
import com.example.user.repository.UserRepository;
import com.example.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
    public class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private UserService userService;
    @Test
    void testFindUserById() {
        // Arrange
        User user = new User(1L, "John", "john@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<UserDTO> result = userService.getUserById(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("John");
        verify(userRepository).findById(1L);
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        UserCreateDTO dto = new UserCreateDTO("Alice", "alice@example.com");
        User savedUser = new User(788L, "Alice", "alice@example.com");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        UserDTO result = userService.createUser(dto);

        // Assert
        assertThat(result.getId()).isEqualTo(788L);
        assertThat(result.getName()).isEqualTo("Alice");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testCreateUser_NullName() {
        UserCreateDTO dto = new UserCreateDTO(null, "alice@example.com");

        assertThrows(IllegalArgumentException.class, () ->
                userService.createUser(dto)
        );
    }

    @Test
    void testCreateUser_InvalidEmail() {
        UserCreateDTO dto = new UserCreateDTO("Alice", "invalid-email");

        assertThrows(IllegalArgumentException.class, () ->
                userService.createUser(dto)
        );
    }
}
