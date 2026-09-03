package baitap.com.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

import baitap.com.entity.UserModel;
import baitap.com.models.UserService;
import baitap.com.service.ProductService;
import baitap.com.service.impl.ProductServiceImpl;

import java.io.IOException;

@WebServlet(urlPatterns = {"", "/", "/home", "/login", "/register", "/activate", "/forgot-password", "/reset-password", "/error", "/logout"})
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserService userService = new UserService();
    private ProductService productService = new ProductServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/logout".equals(path)) {
            req.getSession().invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        if ("/login".equals(path)) {
            if ("true".equals(req.getParameter("registered"))) {
                req.setAttribute("success", "Đăng ký thành công. Hãy đăng nhập");
            }
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("username".equals(cookie.getName())) {
                        req.setAttribute("rememberedUsername", cookie.getValue());
                        req.setAttribute("rememberedLogin", true);
                        break;
                    }
                }
            }
        }

        switch (path) {
            case "/login":
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
                break;
            case "/register":
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
                break;
            case "/activate":
                req.getRequestDispatcher("/views/activate.jsp").forward(req, resp);
                break;
            case "/forgot-password":
                req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp);
                break;
            case "/reset-password":
                req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp);
                break;
            case "/error":
                req.getRequestDispatcher("/views/error.jsp").forward(req, resp);
                break;
            case "/home":
            case "/":
            default:
                req.setAttribute("products", productService.getLatest(10));
                req.getRequestDispatcher("/views/index.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        String path = req.getServletPath();

        if ("/login".equals(path)) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            UserModel user = userService.login(username, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("account", user);
                session.setAttribute("username", user.getUsername());
                Cookie cookie = new Cookie("username", user.getUsername());
                cookie.setMaxAge(30 * 24 * 60 * 60);
                cookie.setPath(req.getContextPath().isEmpty() ? "/" : req.getContextPath());
                cookie.setHttpOnly(true);
                resp.addCookie(cookie);
                resp.sendRedirect(req.getContextPath() + "/waiting");
            } else {
                req.setAttribute("alert", "Tài khoản hoặc mật khẩu không đúng");
                req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        } else if ("/register".equals(path)) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirmPassword");

            if (password == null || !password.equals(confirmPassword)) {
                req.setAttribute("alert", "Mật khẩu xác nhận không khớp");
                req.setAttribute("username", username);
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            } else if (!userService.register(username, email, password)) {
                req.setAttribute("alert", "Username đã tồn tại hoặc không thể tạo tài khoản");
                req.setAttribute("username", username);
                req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/activate?username=" + java.net.URLEncoder.encode(username, java.nio.charset.StandardCharsets.UTF_8));
            }
        } else if ("/activate".equals(path)) {
            if (userService.activate(req.getParameter("username"), req.getParameter("otp"))) {
                resp.sendRedirect(req.getContextPath() + "/login?registered=true");
            } else { req.setAttribute("alert", "OTP không đúng hoặc đã hết hạn"); req.getRequestDispatcher("/views/activate.jsp").forward(req, resp); }
        } else if ("/forgot-password".equals(path)) {
            if (userService.requestPasswordReset(req.getParameter("username"))) {
                resp.sendRedirect(req.getContextPath() + "/reset-password?username=" + java.net.URLEncoder.encode(req.getParameter("username"), java.nio.charset.StandardCharsets.UTF_8));
            } else { req.setAttribute("alert", "Không tìm thấy tài khoản có email"); req.getRequestDispatcher("/views/forgot-password.jsp").forward(req, resp); }
        } else if ("/reset-password".equals(path)) {
            if (userService.resetPassword(req.getParameter("username"), req.getParameter("otp"), req.getParameter("password"))) {
                resp.sendRedirect(req.getContextPath() + "/login?registered=true");
            } else { req.setAttribute("alert", "OTP không đúng hoặc đã hết hạn"); req.getRequestDispatcher("/views/reset-password.jsp").forward(req, resp); }
        }
    }
}