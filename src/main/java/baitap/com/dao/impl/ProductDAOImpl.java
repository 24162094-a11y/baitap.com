package baitap.com.dao.impl;

import java.util.List;
import baitap.com.config.JpaConfig;
import baitap.com.dao.ProductDAO;
import baitap.com.models.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ProductDAOImpl implements ProductDAO {
    @Override public void insert(Product product) { save(product, false); }
    @Override public void edit(Product product) { save(product, true); }

    private void save(Product product, boolean edit) {
        EntityManager manager = JpaConfig.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            if (!edit) product.setCreatedDate(System.currentTimeMillis());
            transaction.begin();
            if (edit) manager.merge(product); else manager.persist(product);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
            throw new IllegalStateException("Không thể lưu sản phẩm", exception);
        } finally { manager.close(); }
    }

    @Override public void delete(int id) {
        EntityManager manager = JpaConfig.getEntityManager();
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            Product product = manager.find(Product.class, id);
            if (product != null) manager.remove(product);
            transaction.commit();
        } catch (Exception exception) {
            if (transaction.isActive()) transaction.rollback();
            throw new IllegalStateException("Không thể xóa sản phẩm", exception);
        } finally { manager.close(); }
    }

    @Override public Product get(int id) {
        EntityManager manager = JpaConfig.getEntityManager();
        try { return manager.find(Product.class, id); } finally { manager.close(); }
    }

    @Override public List<Product> getLatest(int limit) {
        EntityManager manager = JpaConfig.getEntityManager();
        try { return manager.createQuery("SELECT p FROM Product p ORDER BY p.createdDate DESC", Product.class)
                .setMaxResults(limit).getResultList(); } finally { manager.close(); }
    }

    @Override public List<Product> getByCategory(int categoryId) {
        EntityManager manager = JpaConfig.getEntityManager();
        try { return manager.createQuery("SELECT p FROM Product p WHERE p.category.id = :categoryId ORDER BY p.createdDate DESC", Product.class)
                .setParameter("categoryId", categoryId).getResultList(); }
        finally { manager.close(); }
    }

    @Override public List<Product> getPage(int page, int size) {
        EntityManager manager = JpaConfig.getEntityManager();
        try { return manager.createQuery("SELECT p FROM Product p ORDER BY p.createdDate DESC", Product.class)
                .setFirstResult((page - 1) * size).setMaxResults(size).getResultList(); }
        finally { manager.close(); }
    }

    @Override public long count() {
        EntityManager manager = JpaConfig.getEntityManager();
        try { return manager.createQuery("SELECT COUNT(p) FROM Product p", Long.class).getSingleResult(); }
        finally { manager.close(); }
    }
}