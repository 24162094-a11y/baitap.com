<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<title>Hồ sơ cá nhân</title>
<style>
    .profile-shell { max-width: 760px; }
    .profile-header { background: linear-gradient(135deg, #0d6efd, #084298); }
    .avatar-preview { width: 112px; height: 112px; object-fit: cover; border: 4px solid #fff; box-shadow: 0 0.5rem 1rem rgba(0,0,0,.15); }
</style>
<div class="row justify-content-center"><div class="col-lg-8 profile-shell"><div class="card border-0 shadow-sm overflow-hidden">
    <div class="profile-header text-white p-4"><p class="text-uppercase small mb-2 opacity-75">Tài khoản</p><h1 class="h3 mb-0">Hồ sơ cá nhân</h1></div>
    <div class="card-body p-4 p-md-5">
    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
    <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
    <c:if test="${not empty profile.avatar}"><img src="${pageContext.request.contextPath}/image?fname=${profile.avatar}" class="rounded-circle avatar-preview mb-3" alt="Ảnh đại diện"></c:if>
    <div class="bg-light rounded p-3 mb-4"><p class="mb-1"><strong>Tài khoản:</strong> ${profile.username}</p><p class="mb-0"><strong>Email:</strong> ${profile.email}</p></div>
    <form method="post" enctype="multipart/form-data" novalidate>
        <label class="form-label fw-semibold">Họ và tên</label><input class="form-control form-control-lg mb-3" name="fullname" value="${profile.fullname}" maxlength="255" required>
        <label class="form-label fw-semibold">Số điện thoại</label><input class="form-control form-control-lg mb-3" name="phone" value="${profile.phone}" pattern="0[0-9]{9,10}" required>
        <label class="form-label fw-semibold">Ảnh đại diện</label><input class="form-control mb-4" type="file" name="avatar" accept="image/jpeg,image/png,image/gif,image/webp">
        <button class="btn btn-primary btn-lg">Cập nhật hồ sơ</button><a class="btn btn-outline-secondary btn-lg ms-2" href="${pageContext.request.contextPath}/">Quay lại</a>
    </form>
    </div></div></div></div>