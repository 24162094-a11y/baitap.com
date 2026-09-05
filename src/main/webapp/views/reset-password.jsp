<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Đặt lại mật khẩu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<main class="card shadow-sm p-4 mx-auto mt-5" style="max-width:420px">
    <h3>Đặt lại mật khẩu</h3>
    <p>Nhập OTP đã gửi vào email và mật khẩu mới.</p>

    <form method="post" action="${pageContext.request.contextPath}/reset-password">
        <input type="hidden" name="username" value="${param.username}">
        <input class="form-control mb-3" name="otp" maxlength="6" pattern="[0-9]{6}" required placeholder="OTP 6 số">
        <input class="form-control mb-3" type="password" name="password" minlength="6" required placeholder="Mật khẩu mới">
        <button class="btn btn-primary w-100" type="submit">Đặt lại mật khẩu</button>
    </form>

    <c:if test="${not empty alert}">
        <div class="alert alert-danger mt-3">${alert}</div>
    </c:if>

    <div class="d-flex justify-content-between mt-3">
        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        <a href="${pageContext.request.contextPath}/">Trang chủ</a>
    </div>
</main>
</body>
</html>
