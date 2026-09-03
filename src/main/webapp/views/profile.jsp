<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<title>Hồ sơ cá nhân</title>
<div class="row justify-content-center"><div class="col-lg-7"><div class="card shadow-sm"><div class="card-body p-4">
    <h1 class="h3 mb-4">Hồ sơ cá nhân</h1>
    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
    <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
    <c:if test="${not empty profile.avatar}"><img src="${pageContext.request.contextPath}/image?fname=${profile.avatar}" class="rounded-circle mb-3" style="width:100px;height:100px;object-fit:cover" alt="Ảnh đại diện"></c:if>
    <p class="text-secondary">Tài khoản: ${profile.username}</p><p class="text-secondary">Email: ${profile.email}</p>
    <form method="post" enctype="multipart/form-data" novalidate>
        <label class="form-label">Họ và tên</label><input class="form-control mb-3" name="fullname" value="${profile.fullname}" maxlength="255" required>
        <label class="form-label">Số điện thoại</label><input class="form-control mb-3" name="phone" value="${profile.phone}" pattern="0[0-9]{9,10}" required>
        <label class="form-label">Ảnh đại diện</label><input class="form-control mb-4" type="file" name="avatar" accept="image/jpeg,image/png,image/gif,image/webp">
        <button class="btn btn-primary">Cập nhật hồ sơ</button><a class="btn btn-outline-secondary ms-2" href="${pageContext.request.contextPath}/">Quay lại</a>
    </form>
</div></div></div></div>