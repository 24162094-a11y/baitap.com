<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.sitemesh.org/sitemesh-3.0" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><sitemesh:write property="title" /></title>
    <sitemesh:write property="head" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-primary"><div class="container"><a class="navbar-brand" href="${pageContext.request.contextPath}/">Bài tập</a><a class="btn btn-light btn-sm" href="${pageContext.request.contextPath}/logout">Đăng xuất</a></div></nav>
    <main class="container py-4"><sitemesh:write property="body" /></main>
</body>
</html>