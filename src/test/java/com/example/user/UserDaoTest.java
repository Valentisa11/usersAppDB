package com.example.user;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Session;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserDaoTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("user")
            .withPassword("pass");

    private SessionFactory sessionFactory;
    private UserDao userDao;

    @BeforeEach
    void setup() {

        Properties props = new Properties();
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.connection.url", postgres.getJdbcUrl());
        props.put("hibernate.connection.username", postgres.getUsername());
        props.put("hibernate.connection.password", postgres.getPassword());
        props.put("hibernate.hbm2ddl.auto", "update");

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySettings(props)
                .build();

        sessionFactory = new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .buildMetadata()
                .buildSessionFactory();

        UserDao userDao = new UserDao(sessionFactory);

    }

    @AfterEach
    void cleanup() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            tx.commit();
            System.out.println("Тестовые данные удалены.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void createUser() {
        //подготовка
        User user = new User(23232323,"Bob", "4@example.com", 55);

        //действие
        UserDao userDao = new UserDao();
        userDao.createUser(user);

        //результат
        User savedUser = userDao.getUserById(user.getId());
        assertNotNull(savedUser);
        assertEquals("Bob", savedUser.getName());


    }

    @Test
    void getUserById() {

        User user = new User(23232323,"Bob", "4@example.com", 55);
        userDao.createUser(user);

        User foundUser = userDao.getUserById(user.getId());

        assertNotNull(foundUser);
        assertEquals("Bob", foundUser.getName());

    }

    @Test
    void getUsersByName() {
        User robin1 = new User("Robin", "df@fdfdf.ru", 36);
        User robin2 = new User("Robin", "df@fd.ru", 44);
        userDao.createUser(robin1);
        userDao.createUser(robin2);

        List<User> users = userDao.getUsersByName("Robin");

        assertEquals(2, users.size());
        assertTrue(users.stream().allMatch(user -> "Robin".equals(user.getName())));
}


    @Test
    void getAllUsers() {
        User user1 = new User(343434, "Fedor", "dfd@df.ru", 22);
        User user2 = new User(458224, "Elena", "elen@df.ru", 22);
        User user3 = new User(145698, "Daniil", "ptrp@df.ru", 32);
        userDao.createUser(user1);
        userDao.createUser(user2);
        userDao.createUser(user3);

        List<User> users = userDao.getAllUsers();

        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
        assertTrue(users.contains(user3));


        }


    @Test
    void updateUser() {

        User user = new User(4553434,"Gorge", "wer@wer.ru", 25);
        userDao.createUser(user);

        user.setName("Michel");
        user.setAge(18);
        user.setEmail("Mmm@nn.ru");

        userDao.updateUser(user);

        User updatedUser = userDao.getUserById(user.getId());
        assertEquals("Michel", updatedUser);
        assertEquals(18, updatedUser);
        assertEquals("Mmm@nn.ru", updatedUser);

    }

    @Test
    void deleteUser() {
        User user = new User(4577777,"Zoe", "zoezoe@wer.ru", 15);
        userDao.createUser(user);

        userDao.deleteUser(user.getId());

        assertNull(userDao.getUserById(user.getId()));

        }
}