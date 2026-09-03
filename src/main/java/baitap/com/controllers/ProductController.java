package baitap.com.controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import baitap.com.service.ProductService;
import baitap.com.service.impl.ProductServiceImpl;

@WebServlet({"/product", "/product/detail"})
public class ProductController extends HttpServlet {
    private final ProductService service = new ProductServiceImpl();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/product/detail".equals(request.getServletPath())) {
            request.setAttribute("product", service.get(Integer.parseInt(request.getParameter("id"))));
            request.getRequestDispatcher("/views/product-detail.jsp").forward(request, response);
            return;
        }
        int page = 1;
        try { page = Math.max(1, Integer.parseInt(request.getParameter("page"))); } catch (Exception ignored) { }
        String categoryId = request.getParameter("categoryId");
        request.setAttribute("products", categoryId == null ? service.getPage(page, 6) : service.getByCategory(Integer.parseInt(categoryId)));
        request.setAttribute("page", page);
        request.setAttribute("totalPages", (service.count() + 5) / 6);
        request.getRequestDispatcher("/views/product.jsp").forward(request, response);
    }
}