<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html><html lang="vi"><head><meta charset="UTF-8"><title>Admin</title></head>
<body style="font-family:Arial;max-width:1000px;margin:30px auto"><h2>Khu vực quản trị</h2><p>Xin chào <b>${sessionScope.username}</b></p>
<ul><li><a href="${pageContext.request.contextPath}/sinh-vien">Sinh viên</a></li><li><a href="${pageContext.request.contextPath}/sach">Sách</a></li><li><a href="${pageContext.request.contextPath}/san-pham">Sản phẩm</a></li></ul>
<a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></body></html>
