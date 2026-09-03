package baitap.com.service.impl;

import java.util.List;
import baitap.com.dao.ProductDAO;
import baitap.com.dao.impl.ProductDAOImpl;
import baitap.com.models.Product;
import baitap.com.service.ProductService;

public class ProductServiceImpl implements ProductService {
    private final ProductDAO dao = new ProductDAOImpl();
    @Override public void insert(Product product) { dao.insert(product); }
    @Override public void edit(Product product) { dao.edit(product); }
    @Override public void delete(int id) { dao.delete(id); }
    @Override public Product get(int id) { return dao.get(id); }
    @Override public List<Product> getLatest(int limit) { return dao.getLatest(limit); }
    @Override public List<Product> getByCategory(int categoryId) { return dao.getByCategory(categoryId); }
    @Override public List<Product> getPage(int page, int size) { return dao.getPage(page, size); }
    @Override public long count() { return dao.count(); }
}