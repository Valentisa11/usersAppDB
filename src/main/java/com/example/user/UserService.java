package com.example.user;

public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    // Создание пользователя с валидацией
    public User createUser(String name, String email, int age) {
        validateUser(name, email, age);
        User user = new User(name, email, age);
        userDao.createUser(user);
        return user;

    }

    // Получение пользователя по ID
    public User getUserById(Long id) {
        User user = userDao.getUserById(id);
        if (user == null) {
            throw new RuntimeException("Пользователь с ID " + id + " не найден");
        }
        return user;
    }

    // Обновление пользователя
    public void updateUser(User user) {
        validateUser(user.getName(), user.getEmail(), user.getAge());
        userDao.updateUser(user);
    }

    // Удаление пользователя
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userDao.deleteUser(id);
    }

    // Валидация данных
    private void validateUser(String name, String email, int age) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Некорректный email");
        }
        if (age < 0 || age > 120) {
            throw new IllegalArgumentException("Возраст должен быть между 0 и 120");
        }
    }
}