<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Danh mục sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<main class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <div><p class="text-secondary mb-1">CỬA HÀNG</p><h1 class="h3 mb-0">Danh mục sản phẩm</h1></div>
        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/">Trang chủ</a>
    </div>
    <div class="row g-4">
        <c:forEach items="${categories}" var="category">
            <div class="col-sm-6 col-lg-4">
                <div class="card h-100 shadow-sm">
                    <c:if test="${not empty category.icon}"><img src="${pageContext.request.contextPath}/image?fname=${category.icon}" class="card-img-top" style="height:180px;object-fit:cover" alt="${category.name}"></c:if>
                    <div class="card-body"><h2 class="h5">${category.name}</h2><c:choose><c:when test="${empty categoryProducts[category.id]}"><p class="text-secondary mb-2">Chưa có sản phẩm.</p></c:when><c:otherwise><div class="list-group list-group-flush mb-3"><c:forEach items="${categoryProducts[category.id]}" var="product"><a class="list-group-item list-group-item-action px-0" href="${pageContext.request.contextPath}/product/detail?id=${product.id}">${product.name} <span class="text-primary float-end">${product.price} đ</span></a></c:forEach></div></c:otherwise></c:choose><a href="${pageContext.request.contextPath}/product?categoryId=${category.id}" class="btn btn-sm btn-primary">Xem sản phẩm</a></div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${empty categories}"><p class="text-secondary">Chưa có danh mục.</p></c:if>
    </div>
</main>
</body>
</html>