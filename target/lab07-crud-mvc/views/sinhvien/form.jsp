<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html><html lang="vi"><head><meta charset="UTF-8"><title>Form sinh viên</title></head><body style="font-family:Arial;max-width:1000px;margin:30px auto">
<h2>${empty sv ? 'Thêm sinh viên':'Sửa sinh viên'}</h2><p style="color:red">${error}</p><form method="post" action="${pageContext.request.contextPath}/sinh-vien">
<input type="hidden" name="id" value="${sv.id}"><p>Mã SV: <input name="maSinhVien" value="${sv.maSinhVien}" required></p><p>Họ tên: <input name="hoTen" value="${sv.hoTen}" required></p>
<p>Email: <input type="email" name="email" value="${sv.email}"></p><p>Lớp: <input name="lop" value="${sv.lop}"></p><button>Lưu</button></form><a href="${pageContext.request.contextPath}/sinh-vien">Quay lại</a></body></html>
