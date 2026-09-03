package baitap.com.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class JpaConfig {

    private static final EntityManagerFactory FACTORY =
        Persistence.createEntityManagerFactory("jpa-hibernate-mysql", java.util.Map.of(
            "jakarta.persistence.jdbc.password",
                    "quocphu0602@"));

    private JpaConfig() {
    }

    public static EntityManager getEntityManager() {
        return FACTORY.createEntityManager();
    }

    public static void shutdown() {
        if (FACTORY.isOpen()) {
            FACTORY.close();
        }
    }
}