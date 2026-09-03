package baitap.com.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import baitap.com.models.Category;
import baitap.com.models.Product;
import baitap.com.service.CategoryService;
import baitap.com.service.ProductService;
import baitap.com.service.impl.CategoryServiceImpl;
import baitap.com.service.impl.ProductServiceImpl;
import baitap.com.util.Constant;

@WebServlet("/admin/product/*")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 6 * 1024 * 1024)
public class ProductAdminController extends HttpServlet {
    private final ProductService service = new ProductServiceImpl();
    private final CategoryService categories = new CategoryServiceImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getPathInfo();
        if ("/delete".equals(path)) { service.delete(Integer.parseInt(request.getParameter("id"))); response.sendRedirect(request.getContextPath() + "/admin/product/list"); return; }
        if ("/add".equals(path) || "/edit".equals(path)) {
            request.setAttribute("categories", categories.getAll());
            if ("/edit".equals(path)) request.setAttribute("product", service.get(Integer.parseInt(request.getParameter("id"))));
            request.getRequestDispatcher("/views/admin/product-form.jsp").forward(request, response); return;
        }
        request.setAttribute("products", service.getPage(1, 1000));
        request.getRequestDispatcher("/views/admin/product-list.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Product product = new Product();
        String id = request.getParameter("id");
        if (id != null && !id.isBlank()) product = service.get(Integer.parseInt(id));
        product.setName(request.getParameter("name")); product.setDescription(request.getParameter("description"));
        product.setPrice(new BigDecimal(request.getParameter("price"))); product.setCategory(categories.get(Integer.parseInt(request.getParameter("categoryId"))));
        Part image = request.getPart("image");
        if (image != null && image.getSize() > 0 && image.getSubmittedFileName() != null) {
            String original = new File(image.getSubmittedFileName()).getName();
            String extension = original.contains(".") ? original.substring(original.lastIndexOf('.')) : ".bin";
            String fileName = System.currentTimeMillis() + extension;
            File directory = new File(Constant.DIR, "product"); directory.mkdirs(); image.write(new File(directory, fileName).getAbsolutePath()); product.setImage("product/" + fileName);
        }
        if (id == null || id.isBlank()) service.insert(product); else service.edit(product);
        response.sendRedirect(request.getContextPath() + "/admin/product/list");
    }
}