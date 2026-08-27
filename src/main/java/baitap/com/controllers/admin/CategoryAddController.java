package baitap.com.controllers.admin;

import java.io.IOException;
import java.io.File;
import java.nio.charset.StandardCharsets;

import baitap.com.models.Category;
import baitap.com.service.CategoryService;
import baitap.com.service.impl.CategoryServiceImpl;
import baitap.com.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/admin/category/add" })
@MultipartConfig
public class CategoryAddController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/add-category.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Category category = new Category();
		try {
			resp.setContentType("text/html");
			resp.setCharacterEncoding("UTF-8");
			req.setCharacterEncoding("UTF-8");
			for (Part item : req.getParts()) {
				if (item.getName().equals("name")) {
					category.setName(new String(item.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
				} else if (item.getName().equals("icon")) {
					if (item.getSize() == 0 || item.getSubmittedFileName() == null) {
						continue;
					}
					String originalFileName = item.getSubmittedFileName();
					int index = originalFileName.lastIndexOf(".");
					String ext = originalFileName.substring(index + 1);
					String fileName = System.currentTimeMillis() + "." + ext;
					File directory = new File(Constant.DIR, "category");
					directory.mkdirs();
					File file = new File(directory, fileName);
					item.write(file.getAbsolutePath());
					category.setIcon("category/" + fileName);
				}
			}
			if (category.getName() == null || category.getName().trim().isEmpty()) {
				req.setAttribute("error", "Vui lòng nhập tên danh mục");
				req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
				return;
			}
			cateService.insert(category);
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
		} catch (Exception e) {
			req.setAttribute("error", "Không thể thêm danh mục vào database");
			req.getRequestDispatcher("/views/admin/add-category.jsp").forward(req, resp);
		}
	}
}