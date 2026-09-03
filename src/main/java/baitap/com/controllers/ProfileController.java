package baitap.com.controllers;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import baitap.com.entity.UserModel;
import baitap.com.models.UserProfile;
import baitap.com.service.UserProfileService;
import baitap.com.service.impl.UserProfileServiceImpl;
import baitap.com.util.Constant;

@WebServlet("/profile")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 6 * 1024 * 1024)
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserProfileService service = new UserProfileServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserProfile profile = loadProfile(request, response);
        if (profile == null) return;
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserProfile profile = loadProfile(request, response);
        if (profile == null) return;
        request.setCharacterEncoding("UTF-8");
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String error = validate(fullname, phone);
        if (error != null) {
            request.setAttribute("profile", profile);
            request.setAttribute("error", error);
            request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
            return;
        }
        profile.setFullname(fullname.trim());
        profile.setPhone(phone.trim());
        Part avatar = request.getPart("avatar");
        if (avatar != null && avatar.getSize() > 0 && avatar.getSubmittedFileName() != null) {
            String original = new File(avatar.getSubmittedFileName()).getName();
            String extension = original.contains(".") ? original.substring(original.lastIndexOf('.')).toLowerCase() : "";
            if (!extension.matches("\\.(jpg|jpeg|png|gif|webp)")) {
                request.setAttribute("profile", profile);
                request.setAttribute("error", "Ảnh phải có định dạng jpg, jpeg, png, gif hoặc webp");
                request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
                return;
            }
            String fileName = System.currentTimeMillis() + extension;
            File directory = new File(Constant.DIR, "avatar");
            directory.mkdirs();
            avatar.write(new File(directory, fileName).getAbsolutePath());
            profile.setAvatar("avatar/" + fileName);
        }
        service.update(profile);
        request.setAttribute("success", "Cập nhật thông tin thành công");
        request.setAttribute("profile", profile);
        request.getRequestDispatcher("/views/profile.jsp").forward(request, response);
    }

    private UserProfile loadProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        UserModel account = session == null ? null : (UserModel) session.getAttribute("account");
        if (account == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return null;
        }
        return service.findByUsername(account.getUsername());
    }

    private String validate(String fullname, String phone) {
        if (fullname == null || fullname.trim().isEmpty()) return "Vui lòng nhập họ tên";
        if (fullname.trim().length() > 255) return "Họ tên không được vượt quá 255 ký tự";
        if (phone == null || !phone.trim().matches("0[0-9]{9,10}")) return "Số điện thoại phải có 10 hoặc 11 chữ số và bắt đầu bằng 0";
        return null;
    }
}