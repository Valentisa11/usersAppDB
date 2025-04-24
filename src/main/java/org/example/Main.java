package org.example;

import com.example.user.HibernateSessionFactory;
import com.example.user.User;
import com.example.user.UserDao;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new com.example.user.UserDao();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВыберите операцию: ");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Получить пользователя по ID");
            System.out.println("3. Получить всех пользователей");
            System.out.println("4. Обновить пользователя");
            System.out.println("5. Поиск пользователей по имени");
            System.out.println("6. Удалить пользователя");
            System.out.println("7. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> createUser(scanner, userDao);
                case 2 -> getUserById(scanner, userDao);
                case 3 -> getAllUsers(userDao);
                case 4 -> updateUser(scanner, userDao);
                case 5 -> getUserByName(scanner, userDao);
                case 6 -> deleteUser(scanner, userDao);
                case 7 -> {
                    HibernateSessionFactory.shutdown();
                    System.out.println("Выход из программы.");
                    return;
                }
                default -> System.out.println("Такого пункта не существует. Попробуйте еще раз.");
            }
        }
    }

    private static void createUser(Scanner scanner, UserDao userDao) {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();
        System.out.println("Введите email: ");
        String email = scanner.nextLine();
        System.out.println("Введите возраст: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        User user = new User(name, email, age);
        userDao.createUser(user);
        System.out.println("Пользователь создан: " + user);
    }

    private static void getUserById(Scanner scanner, UserDao userDao) {
        System.out.println("Введите ID пользователя: ");
        Long id = scanner.nextLong();
        User user = userDao.getUserById(id);
        if (user != null) {
            System.out.println("Пользователь найден: " + user);
        } else {
            System.out.println("Пользователь не найден.");
        }
    }
    private static void getUserByName(Scanner scanner, UserDao userDao) {
        System.out.println("Введите имя для поиска: ");
        String searchName = scanner.nextLine();
        List<User> usersWithName = userDao.getUsersByName(searchName);

        if (usersWithName.isEmpty()) {
            System.out.println("Пользователи с именем '" + searchName + "' не найдены.");
        } else {
            System.out.println("Пользователи с именем '" + searchName + "':");
            usersWithName.forEach(System.out::println);
        }

    }

    private static void getAllUsers(UserDao userDao) {
        System.out.println("Список пользователей: ");
        userDao.getAllUsers().forEach(System.out::println);
    }

    private static void updateUser(Scanner scanner, UserDao userDao) {
        System.out.println("Введите ID пользователя для обновления: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        User user = userDao.getUserById(id);
        if (user != null) {
            System.out.println("Введите новое имя: ");
            String name = scanner.nextLine();
            System.out.println("Введите новый email: ");
            String email = scanner.nextLine();
            System.out.println("Введите новый возраст: ");
            int age = scanner.nextInt();
            scanner.nextLine();

            user.setName(name);
            user.setEmail(email);
            user.setAge(age);
            userDao.updateUser(user);
            System.out.println("Пользователь обновлен: " + user);
        } else {
            System.out.println("Пользователь не найден.");
        }
    }

    private static void deleteUser(Scanner scanner, com.example.user.UserDao userDao) {
        System.out.println("Введите ID пользователя для удаления: ");
        Long id = scanner.nextLong();
        userDao.deleteUser(id);
        System.out.println("Пользователь удален.");
    }
}