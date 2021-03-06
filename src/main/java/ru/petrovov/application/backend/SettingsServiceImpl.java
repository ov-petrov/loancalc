package ru.petrovov.application.backend;

import ru.petrovov.application.backend.model.Setting;
import ru.petrovov.application.utils.HibernateUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SettingsServiceImpl implements SettingsService {
    private final Map<String, String> settings = new HashMap<>();

    @Inject
    HibernateUtil hibernateUtil;

    @PostConstruct
    public void create() {
        EntityManager entityManager = hibernateUtil.getSessionFactory().createEntityManager();
        TypedQuery<Setting> query = entityManager.createQuery("select s from Setting s", Setting.class);
        query.getResultList().forEach(v -> settings.put(v.getName(), v.getValue()));
        entityManager.close();
    }

    @Override
    public Map<String, String> getSettings() {
        if (settings.isEmpty())
            create();

        return settings;
    }

}
