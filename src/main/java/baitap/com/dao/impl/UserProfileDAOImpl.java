package baitap.com.dao.impl;

import baitap.com.config.JpaConfig;
import baitap.com.dao.UserProfileDAO;
import baitap.com.models.UserProfile;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UserProfileDAOImpl implements UserProfileDAO {
    @Override
    public UserProfile findByUsername(String username) {
        EntityManager manager = JpaConfig.getEntityManager();
        try {
            return manager.createQuery("SELECT u FROM UserProfile u WHERE u.username = :username", UserProfile.class)
                    .setParameter("username", username).setMaxResults(1).getResultStream().findFirst().orElse(null);
        } finally { manager.close(); }
    }

    @Override
    public void update(UserProfile profile) {
        EntityManager manager = JpaConfig.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            manager.merge(profile);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
            throw new IllegalStateException("Không thể cập nhật hồ sơ", exception);
        } finally { manager.close(); }
    }
}