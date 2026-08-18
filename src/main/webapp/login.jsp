<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html><html lang="vi"><head><meta charset="UTF-8"><title>Đăng nhập</title></head>
<body style="font-family:Arial;max-width:1000px;margin:30px auto"><h2>Đăng nhập</h2><p>Demo: <b>admin / 123456</b></p><p style="color:red">${error}</p>
<form method="post" action="${pageContext.request.contextPath}/login"><p>Tài khoản: <input name="username" required></p><p>Mật khẩu: <input type="password" name="password" required></p><button>Đăng nhập</button></form>
<p><a href="${pageContext.request.contextPath}/">Trang chủ</a></p></body></html>
