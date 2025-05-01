package com.example.user;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
    public class UserServiceTest {

        private UserService userService;
        @Mock
        private UserDao mockUserDao;

        @BeforeEach
        void setup() {

            userService = new UserService(mockUserDao);
        }

        @Test
        void testCreateUser_Success() {

            User userCreated = new User("Alice", "alice@example.com", 30);

           doAnswer(invocation -> {
               User userArg = (User) invocation.getArgument(0);
               userArg.setId(788l);
               return userArg;
                   }).when(mockUserDao).createUser(any(User.class));

            User result = userService.createUser("Alice", "alice@example.com", 30);

            assertNotNull(result.getId());
            assertEquals("Alice", result.getName(), "Имя пользователя не совпадает");
            assertEquals("alice@example.com", result.getEmail());
            verify(mockUserDao).createUser(any(User.class));

        }

        @Test
        void testCreateUser_NullName() {
            User user = new User(null,"alice@example.com",30);

            assertThrows(IllegalArgumentException.class, () ->
                    userService.createUser(null,  "alice@example.com", 30)
            );
        }

        @Test
        void testCreateUser_InvalidEmail() {
            User user = new User("Alice", "invalid-email", 30);

            assertThrows(IllegalArgumentException.class, () ->
                    userService.createUser("Alice", "invalid-email", 30)
            );
        }

        @Test
        void testCreateUser_InvalidAge() {

            User user = new User("Alice", "alice@example.com", -5);

            assertThrows(IllegalArgumentException.class, () ->
                    userService.createUser("Alice", "alice@example.com", -5)
            );
        }

        @Test
        void testGetUserById_Success() {

            Long userId = 1L;
            User mockUser = new User(userId, "Alice", "alice@example.com", 30);
            when(mockUserDao.getUserById(userId)).thenReturn(mockUser);

            User result = userService.getUserById(userId);

           // assertEquals(mockUser, result);
           // verify(mockUserDao).getUserById(userId);
            assertEquals("Alice", result.getName());
        }

        @Test
        void testGetUserById_NotFound() {

            Long nonExistentId = 999L;
            when(mockUserDao.getUserById(nonExistentId)).thenReturn(null);

            assertThrows(RuntimeException.class, () ->
                    userService.getUserById(nonExistentId)
            );
        }

        @Test
        void testUpdateUser_Success() {

            User user = new User(1L, "Alice", "alice@example.com", 30);
            userService.updateUser(user);

            verify(mockUserDao).updateUser(user);
        }

        @Test
        void testUpdateUser_NullName() {

            User user = new User(1L, null, "alice@example.com", 30);

            assertThrows(IllegalArgumentException.class, () ->
                    userService.updateUser(user)
            );
        }

        @Test
        void testDeleteUser_Success() {

            Long userId = 1L;
            User mockUser = new User(userId, "Alice", "alice@example.com", 30);
            when(mockUserDao.getUserById(userId)).thenReturn(mockUser);

            userService.deleteUser(userId);

            verify(mockUserDao).deleteUser(userId);
        }

        @Test
        void testDeleteUser_NotFound() {

            Long nonExistentId = 999L;
            when(mockUserDao.getUserById(nonExistentId)).thenReturn(null);

            assertThrows(RuntimeException.class, () ->
                    userService.deleteUser(nonExistentId)
            );
        }
    }