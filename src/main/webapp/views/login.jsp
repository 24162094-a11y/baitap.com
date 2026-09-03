<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light d-flex align-items-center justify-content-center vh-100">
    <div class="card shadow-sm p-4" style="width: 100%; max-width: 400px;">
        <h3 class="card-title text-center mb-4 text-primary fw-bold">ĐĂNG NHẬP</h3>
        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="mb-3">
                <label class="form-label font-weight-bold">Tên đăng nhập</label>
                <input type="text" name="username" class="form-control" placeholder="Nhập username" value="${rememberedUsername}" autocomplete="username" required>
            </div>
            <div class="mb-3">
                <label class="form-label">Mật khẩu</label>
                <input type="password" name="password" class="form-control" placeholder="Nhập password" autocomplete="current-password" required>
            </div>
            <c:if test="${not empty alert}">
                <div class="alert alert-danger">${alert}</div>
            </c:if>
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            <button type="submit" class="btn btn-primary w-100 mt-2">Đăng nhập</button>
        </form>
        <p class="text-center mt-3 mb-0">Chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a></p>
        <p class="text-center mb-0"><a href="${pageContext.request.contextPath}/forgot-password">Quên mật khẩu?</a></p>
    </div>
</body>
</html>