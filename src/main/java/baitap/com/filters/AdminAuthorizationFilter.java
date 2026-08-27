package baitap.com.filters;

import java.io.IOException;

import baitap.com.entity.UserModel;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class AdminAuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        UserModel user = session == null ? null : (UserModel) session.getAttribute("account");

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        } else if (user.getRoleid() != 1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
        } else {
            chain.doFilter(request, response);
        }
    }
}
