package baitap.com.dao;

import java.util.List;
import baitap.com.models.Product;

public interface ProductDAO {
    void insert(Product product);
    void edit(Product product);
    void delete(int id);
    Product get(int id);
    List<Product> getLatest(int limit);
    List<Product> getByCategory(int categoryId);
    List<Product> getPage(int page, int size);
    long count();
}