package baitap.com.service.impl;

import java.io.File;
import java.util.List;
import baitap.com.dao.CategoryDAO;
import baitap.com.dao.impl.CategoryDAOImpl;
import baitap.com.models.Category;
import baitap.com.service.CategoryService;
import baitap.com.util.Constant;

public class CategoryServiceImpl implements CategoryService {
	CategoryDAO categoryDAO = new CategoryDAOImpl();

	@Override
	public void insert(Category category) {
		categoryDAO.insert(category);
	}

	@Override
	public void delete(int id) {
		categoryDAO.delete(id);
	}

	@Override
	public Category get(int id) {
		return categoryDAO.get(id);
	}

	@Override
	public Category get(String name) {
		return categoryDAO.get(name);
	}

	@Override
	public List<Category> getAll() {
	return categoryDAO.getAll();
	}
	@Override
	public List<Category> search(String catename) {
	return categoryDAO.search(catename);
	}
	
	@Override
	public void edit(Category newCategory) {
		Category oldCategory = categoryDAO.get(newCategory.getId());
		oldCategory.setName(newCategory.getName());
		if (newCategory.getIcon() != null) {
			// XOA ANH CU DI
			String fileName = oldCategory.getIcon();
			File file = new File(Constant.DIR, fileName);
			if (file.exists()) {
				file.delete();
			}
			oldCategory.setIcon(newCategory.getIcon());
		}
		categoryDAO.edit(oldCategory);
	}
}