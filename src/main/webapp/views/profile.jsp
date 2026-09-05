<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<title>Hồ sơ cá nhân</title>
<style>
    .profile-shell { max-width: 920px; }
    .profile-card { border-radius: 18px; }
    .profile-aside { background: linear-gradient(145deg, #0b3d91, #087f8c); min-height: 100%; }
    .avatar-preview, .avatar-placeholder { width: 128px; height: 128px; object-fit: cover; border: 5px solid rgba(255,255,255,.85); box-shadow: 0 0.75rem 1.5rem rgba(0,0,0,.2); }
    .avatar-placeholder { display: flex; align-items: center; justify-content: center; background: rgba(255,255,255,.2); font-size: 3rem; font-weight: 700; }
    .profile-label { color: #64748b; font-size: .8rem; letter-spacing: .08em; text-transform: uppercase; }
    .profile-form .form-control { border-radius: 10px; }
    @media (max-width: 767px) { .profile-aside { min-height: auto; } }
</style>
<div class="row justify-content-center"><div class="col-12 profile-shell"><div class="card profile-card border-0 shadow-sm overflow-hidden"><div class="row g-0">
    <aside class="col-md-4 profile-aside text-white p-4 p-md-5 text-center d-flex flex-column align-items-center justify-content-center">
        <p class="profile-label text-white opacity-75 mb-2">Tài khoản của bạn</p>
        <c:choose><c:when test="${not empty profile.avatar}"><img src="${pageContext.request.contextPath}/image?fname=${profile.avatar}" class="rounded-circle avatar-preview mb-3" alt="Ảnh đại diện"></c:when><c:otherwise><div class="rounded-circle avatar-placeholder mb-3">${profile.username.substring(0,1).toUpperCase()}</div></c:otherwise></c:choose>
        <h1 class="h4 mb-1">${profile.username}</h1><p class="mb-0 opacity-75">Hồ sơ cá nhân</p>
    </aside>
    <section class="col-md-8"><div class="card-body p-4 p-md-5">
    <div class="mb-4"><p class="profile-label mb-2">Thông tin cá nhân</p><h2 class="h3 mb-0">Cập nhật hồ sơ</h2></div>
    <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
    <c:if test="${not empty success}"><div class="alert alert-success">${success}</div></c:if>
    <div class="bg-light rounded-3 p-3 mb-4"><p class="mb-1"><span class="profile-label d-block">Email</span><strong>${profile.email}</strong></p></div>
    <form class="profile-form" method="post" enctype="multipart/form-data" novalidate>
        <label class="form-label fw-semibold" for="fullname">Họ và tên</label><input id="fullname" class="form-control form-control-lg mb-3" name="fullname" value="${profile.fullname}" maxlength="255" required>
        <label class="form-label fw-semibold" for="phone">Số điện thoại</label><input id="phone" class="form-control form-control-lg mb-3" name="phone" value="${profile.phone}" pattern="0[0-9]{9,10}" required>
        <label class="form-label fw-semibold" for="avatar">Ảnh đại diện</label><input id="avatar" class="form-control mb-4" type="file" name="avatar" accept="image/jpeg,image/png,image/gif,image/webp">
        <button class="btn btn-primary px-4">Lưu thay đổi</button><a class="btn btn-outline-secondary ms-2" href="${pageContext.request.contextPath}/">Quay lại</a>
    </form>
    </div></section></div></div></div></div>