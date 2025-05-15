package com.example.user;


import com.example.user.dto.UserCreateDTO;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleRunner implements CommandLineRunner {
    private UserService userService;
    private Scanner scanner = new Scanner(System.in);

    @Override
        public void run(String... args) {
            while (true) {
                System.out.println("\nВыберите операцию: ");
                System.out.println("1. Создать пользователя");
                System.out.println("2. Получить пользователя по ID");
                System.out.println("3. Получить всех пользователей");
                System.out.println("4. Обновить пользователя");
                System.out.println("5. Удалить пользователя");
                System.out.println("6. Выход");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 -> createUser();
                    case 2 -> getUserById();
                    case 3 -> getAllUsers();
                    case 4 -> updateUser();
                    case 5 -> deleteUser();
                    case 6 -> {
                        System.out.println("Выход из программы.");
                        return;
                    }
                    default -> System.out.println("Такого пункта не существует.");
                }
            }
        }

        private void createUser() {
            System.out.println("Введите имя: ");
            String name = scanner.nextLine();
            System.out.println("Введите email: ");
            String email = scanner.nextLine();

            UserCreateDTO dto = new UserCreateDTO(name, email);
            userService.createUser(dto);
            System.out.println("Пользователь создан.");
        }

        private void getUserById() {
            System.out.println("Введите ID пользователя: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            userService.getUserById(id)
                    .ifPresentOrElse(
                            user -> System.out.println("Пользователь: " + user),
                            () -> System.out.println("Пользователь не найден.")
                    );
        }

        private void getAllUsers() {
            System.out.println("Список пользователей:");
            userService.getAllUsers().forEach(System.out::println);
        }

        private void updateUser() {
            System.out.println("Введите ID пользователя для обновления: ");
            Long id = scanner.nextLong();
            scanner.nextLine();

            userService.getUserById(id).ifPresent(user -> {
                System.out.println("Введите новое имя: ");
                String name = scanner.nextLine();
                System.out.println("Введите новый email: ");
                String email = scanner.nextLine();

                UserCreateDTO dto = new UserCreateDTO(name, email);
                userService.updateUser(id, dto);
                System.out.println("Пользователь обновлен.");
            });
        }

        private void deleteUser() {
            System.out.println("Введите ID пользователя для удаления: ");
            Long id = scanner.nextLong();
            userService.deleteUser(id);
            System.out.println("Пользователь удален.");
        }
}
