<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bảng điều khiển</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: #f4f7fb; }
        .sidebar { min-height: 100vh; background: #172554; }
        .sidebar a { color: #cbd5e1; text-decoration: none; }
        .sidebar a:hover { color: #fff; background: #1e3a8a; }
        .welcome { background: linear-gradient(120deg, #2563eb, #0f766e); }
        .stat-card { border: 0; border-left: 4px solid #2563eb; }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <aside class="col-md-3 col-lg-2 sidebar p-0">
                <div class="p-4">
                    <h4 class="text-white mb-4">BaiTap Admin</h4>
                    <nav class="nav flex-column gap-2">
                        <a class="rounded p-2" href="${pageContext.request.contextPath}/home">Tổng quan</a>
                        <a class="rounded p-2" href="${pageContext.request.contextPath}/admin/category/list">Danh mục</a>
                        <c:choose>
                            <c:when test="${not empty sessionScope.account}">
                                <a class="rounded p-2" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                            </c:when>
                            <c:otherwise>
                                <a class="rounded p-2" href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                            </c:otherwise>
                        </c:choose>
                    </nav>
                </div>
            </aside>

            <main class="col-md-9 col-lg-10 p-4 p-lg-5">
                <div class="d-flex justify-content-between align-items-center mb-4">
                    <div>
                        <p class="text-secondary mb-1">BẢNG ĐIỀU KHIỂN</p>
                        <h1 class="h3 mb-0">Xin chào<c:if test="${not empty sessionScope.username}">, ${sessionScope.username}</c:if></h1>
                    </div>
                    <c:choose>
                        <c:when test="${not empty sessionScope.account}"><a class="btn btn-outline-danger" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></c:when>
                        <c:otherwise><a class="btn btn-primary" href="${pageContext.request.contextPath}/login">Đăng nhập</a></c:otherwise>
                    </c:choose>
                </div>

                <section class="welcome text-white rounded-4 p-4 p-lg-5 mb-4 shadow-sm">
                    <h2 class="h4">Chào mừng bạn quay lại</h2>
                    <p class="mb-0">Bạn đã đăng nhập thành công vào hệ thống quản lý.</p>
                </section>

                <div class="row g-4 mb-4">
                    <div class="col-sm-6 col-xl-4">
                        <div class="card stat-card shadow-sm h-100">
                            <div class="card-body">
                                <p class="text-secondary mb-2">Tài khoản hiện tại</p>
                                <h3 class="h5 mb-0">${sessionScope.username}</h3>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-xl-4">
                        <div class="card stat-card shadow-sm h-100">
                            <div class="card-body">
                                <p class="text-secondary mb-2">Trạng thái</p>
                                <h3 class="h5 text-success mb-0">Đang hoạt động</h3>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6 col-xl-4">
                        <div class="card stat-card shadow-sm h-100">
                            <div class="card-body">
                                <p class="text-secondary mb-2">Quyền truy cập</p>
                                <h3 class="h5 mb-0">Quản lý hệ thống</h3>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card border-0 shadow-sm">
                    <div class="card-body p-4">
                        <h2 class="h5">Thao tác nhanh</h2>
                        <p class="text-secondary">Chọn một chức năng để tiếp tục làm việc.</p>
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/list">Mở quản lý danh mục</a>
                    </div>
                </div>
            </main>
        </div>
    </div>
</body>
</html>