package baitap.com.controllers.admin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import baitap.com.models.Category;
import baitap.com.service.CategoryService;
import baitap.com.service.impl.CategoryServiceImpl;
import baitap.com.util.Constant;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet(urlPatterns = { "/admin/category/edit" })
@MultipartConfig
public class CategoryeEditController extends HttpServlet {
	CategoryService cateService = new CategoryServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		Category category = cateService.get(Integer.parseInt(id));
		req.setAttribute("category", category);
		RequestDispatcher dispatcher = req.getRequestDispatcher("/views/admin/edit-Category.jsp");
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

				if (item.getName().equals("id")) {
					category.setId(Integer.parseInt(new String(item.getInputStream().readAllBytes(), StandardCharsets.UTF_8)));
				} else if (item.getName().equals("name")) {
					category.setName(new String(item.getInputStream().readAllBytes(), StandardCharsets.UTF_8));
				} else if (item.getName().equals("icon")) {
					if (item.getSize() > 0) {// neu co file d
						String originalFileName = item.getSubmittedFileName();
						int index = originalFileName.lastIndexOf(".");
						String ext = originalFileName.substring(index + 1);
						String fileName = System.currentTimeMillis() + "." + ext;
						File directory = new File(Constant.DIR, "category");
						directory.mkdirs();
						File file = new File(directory, fileName);
						item.write(file.getAbsolutePath());
						category.setIcon("category/" + fileName);
					} else {
						category.setIcon(null);
					}
				}
			}
			cateService.edit(category);
			resp.sendRedirect(req.getContextPath() + "/admin/category/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}