package com.example.user;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class UserDao {

    private final SessionFactory sessionFactory;

    // Конструктор для основного кода
    public UserDao() {
        this.sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    // Конструктор для тестов
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public User createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            System.err.println("Ошибка создания пользователя: " + e.getMessage());
            return null;
        }
    }

    public User getUserById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        } catch (Exception e) {
            System.err.println("Ошибка получения пользователя по ID: " + e.getMessage());
            return null;
        }
    }
    public List<User> getUsersByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE name = :name", User.class);
            query.setParameter("name", name);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            System.err.println("Ошибка получения всех пользователей: " + e.getMessage());
            return null;
        }

    }

    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка обновления пользователя: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.remove(user);
            }
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Ошибка удаления пользователя: " + e.getMessage());
        }
    }

}