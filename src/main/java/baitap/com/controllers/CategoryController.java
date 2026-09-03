package baitap.com.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import baitap.com.service.CategoryService;
import baitap.com.service.ProductService;
import baitap.com.service.impl.CategoryServiceImpl;
import baitap.com.service.impl.ProductServiceImpl;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CategoryService service = new CategoryServiceImpl();
    private final ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var categories = service.getAll();
        Map<Integer, java.util.List<baitap.com.models.Product>> categoryProducts = new HashMap<>();
        for (var category : categories) categoryProducts.put(category.getId(), productService.getByCategory(category.getId()));
        request.setAttribute("categories", categories);
        request.setAttribute("categoryProducts", categoryProducts);
        request.getRequestDispatcher("/views/category.jsp").forward(request, response);
    }
}