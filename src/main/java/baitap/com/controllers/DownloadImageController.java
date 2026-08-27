package baitap.com.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.IOUtils;

import baitap.com.util.Constant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = "/image") // ?fname=abc.png
public class DownloadImageController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getParameter("fname");
		if (fileName == null || fileName.contains("..")) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		File file = new File(Constant.DIR, fileName);
		if (!file.isFile()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		resp.setContentType(Files.probeContentType(file.toPath()));
		try (FileInputStream input = new FileInputStream(file)) {
			IOUtils.copy(input, resp.getOutputStream());
		}
	}
}