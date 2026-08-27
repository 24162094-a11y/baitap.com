package baitap.com.dao.impl;

import java.util.List;

import baitap.com.config.JpaConfig;
import baitap.com.dao.CategoryDAO;
import baitap.com.models.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public void insert(Category category) {
        EntityManager entityManager = JpaConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            category.setCreatedDate(System.currentTimeMillis());

            transaction.begin();
            entityManager.persist(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new IllegalStateException(
                    "Không thể thêm danh mục vào database", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void edit(Category category) {
        EntityManager entityManager = JpaConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(category);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new IllegalStateException(
                    "Không thể sửa danh mục", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = JpaConfig.getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            Category category = entityManager.find(Category.class, id);

            transaction.begin();

            if (category != null) {
                entityManager.remove(category);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw new IllegalStateException(
                    "Không thể xóa danh mục", e);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Category get(int id) {
        EntityManager entityManager = JpaConfig.getEntityManager();

        try {
            return entityManager.find(Category.class, id);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Category> getAll() {
        EntityManager entityManager = JpaConfig.getEntityManager();

        try {
            return entityManager.createQuery(
                    "SELECT c FROM Category c ORDER BY c.cateid DESC",
                    Category.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Category get(String name) {
        EntityManager entityManager = JpaConfig.getEntityManager();

        try {
            List<Category> categories = entityManager.createQuery(
                    "SELECT c FROM Category c WHERE c.catename = :name",
                    Category.class)
                    .setParameter("name", name)
                    .setMaxResults(1)
                    .getResultList();

            return categories.isEmpty() ? null : categories.get(0);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public List<Category> search(String keyword) {
        EntityManager entityManager = JpaConfig.getEntityManager();

        try {
            return entityManager.createQuery(
                    "SELECT c FROM Category c "
                    + "WHERE c.catename LIKE :keyword "
                    + "ORDER BY c.cateid DESC",
                    Category.class)
                    .setParameter("keyword", "%" + keyword + "%")
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }
}