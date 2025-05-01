package com.example.user;

import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;



public class HibernateSessionFactory {
    //private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;
    public SessionFactory getTestSessionFactory() {
        return sessionFactory;
    }

    static {
        try {
            Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

            serviceRegistry = (ServiceRegistry) new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();


            sessionFactory = configuration.buildSessionFactory();
        } catch (Exception ex) {
            System.err.println("Ошибка инициализации SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        if (serviceRegistry != null) {
            StandardServiceRegistryBuilder.destroy((org.hibernate.service.ServiceRegistry) serviceRegistry);
        }
    }
}

  /*  private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Ошибка при инициализации SessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}*/