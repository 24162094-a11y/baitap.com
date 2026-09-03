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
                    <h4 class="text-white mb-4" data-role-title="true">
                        <c:choose>
                            <c:when test="${empty sessionScope.account}">Chưa đăng nhập</c:when>
                            <c:when test="${sessionScope.account.roleid == 1}">Bài tập - Admin</c:when>
                            <c:otherwise>Bài tập - User</c:otherwise>
                        </c:choose>
                    </h4>
                    <nav class="nav flex-column gap-2">
                        <a class="rounded p-2" href="${pageContext.request.contextPath}/home">Tổng quan</a>
                        <c:choose>
                            <c:when test="${sessionScope.account.roleid == 1}"><a class="rounded p-2" href="${pageContext.request.contextPath}/admin/category/list">Danh mục</a></c:when>
                            <c:otherwise><a class="rounded p-2" href="${pageContext.request.contextPath}/category">Danh mục</a></c:otherwise>
                        </c:choose>
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
                <section class="mt-4">
                    <div class="d-flex justify-content-between align-items-center"><h2 class="h5">10 sản phẩm mới nhất</h2><a href="${pageContext.request.contextPath}/product">Xem tất cả</a></div>
                    <div class="row g-3"><c:forEach items="${products}" var="product"><div class="col-sm-6 col-lg-3"><a class="card h-100 text-decoration-none" href="${pageContext.request.contextPath}/product/detail?id=${product.id}"><c:if test="${not empty product.image}"><img src="${pageContext.request.contextPath}/image?fname=${product.image}" class="card-img-top" style="height:150px;object-fit:cover"></c:if><div class="card-body"><h3 class="h6 text-dark">${product.name}</h3><p class="text-primary mb-0">${product.price} đ</p></div></a></div></c:forEach></div>
                </section>
            </main>
        </div>
    </div>
</body>
</html>