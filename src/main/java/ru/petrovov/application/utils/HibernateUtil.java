package ru.petrovov.application.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;

@ApplicationScoped
public class HibernateUtil {
    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    @PostConstruct
    public void setUp() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder()
                        .build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                e.printStackTrace();
                shutdown();
            }
        }
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            setUp();

        return sessionFactory;
    }

    public void saveEntity(Object entity) {
        EntityManager entityManager = getSessionFactory().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        entityManager.close();
    }


    @PreDestroy
    public void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
