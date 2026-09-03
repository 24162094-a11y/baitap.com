<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Quản lý danh mục</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body { background: #f4f7fb; }
		.sidebar { min-height: 100vh; background: #172554; }
		.sidebar a { color: #cbd5e1; text-decoration: none; }
		.sidebar a:hover { color: #fff; background: #1e3a8a; }
		.category-image { width: 96px; height: 72px; object-fit: cover; background: #e2e8f0; }
	</style>
</head>
<body>
<div class="container-fluid">
	<div class="row">
		<aside class="col-md-3 col-lg-2 sidebar p-0">
			<div class="p-4">
				<h4 class="text-white mb-4">Bài tập - Admin</h4>
				<nav class="nav flex-column gap-2">
					<a class="rounded p-2" href="${pageContext.request.contextPath}/home">Tổng quan</a>
					<a class="rounded p-2 bg-primary" href="${pageContext.request.contextPath}/admin/category/list">Danh mục</a>
					<a class="rounded p-2" href="${pageContext.request.contextPath}/admin/product/list">Sản phẩm</a>
					<a class="rounded p-2" href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
				</nav>
			</div>
		</aside>
		<main class="col-md-9 col-lg-10 p-4 p-lg-5">
			<div class="d-flex flex-wrap justify-content-between align-items-center gap-3 mb-4">
				<div>
					<p class="text-secondary mb-1">QUẢN TRỊ</p>
					<h1 class="h3 mb-0">Danh mục</h1>
				</div>
				<a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/category/add">+ Thêm danh mục</a>
				<a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/admin/product/list">Quản lý sản phẩm</a>
			</div>
			<div class="card border-0 shadow-sm">
				<div class="card-body p-0">
					<div class="table-responsive">
						<table class="table table-hover align-middle mb-0">
							<thead class="table-light">
								<tr><th class="ps-4">#</th><th>Ảnh</th><th>Tên danh mục</th><th class="text-end pe-4">Thao tác</th></tr>
							</thead>
							<tbody>
							<c:forEach items="${cateList}" var="cate" varStatus="STT">
								<tr>
									<td class="ps-4">${STT.index + 1}</td>
									<td>
										<c:if test="${not empty cate.icon}">
											<c:url value="/image?fname=${cate.icon}" var="imgUrl" />
											<img class="category-image rounded" src="${imgUrl}" alt="Ảnh ${cate.name}">
										</c:if>
										<c:if test="${empty cate.icon}"><span class="text-secondary">Chưa có ảnh</span></c:if>
									</td>
									<td class="fw-semibold">${cate.name}</td>
									<td class="text-end pe-4">
										<a class="btn btn-sm btn-outline-primary" href="<c:url value='/admin/category/edit?id=${cate.id}'/>" aria-label="Sửa ${cate.name}">Sửa</a>
										<a class="btn btn-sm btn-outline-danger" href="<c:url value='/admin/category/delete?id=${cate.id}'/>" aria-label="Xóa ${cate.name}">Xóa</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</main>
	</div>
</div>
</body>
</html>