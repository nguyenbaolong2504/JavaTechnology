<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html><html lang="vi"><head><meta charset="UTF-8"><title>Sinh viên</title></head><body style="font-family:Arial;max-width:1000px;margin:30px auto">
<h2>Danh sách sinh viên</h2><form method="get"><input name="keyword" value="${keyword}" placeholder="Mã, tên hoặc lớp"><button>Tìm</button></form>
<p><a href="${pageContext.request.contextPath}/sinh-vien?action=new">+ Thêm sinh viên</a></p>
<table border="1" cellpadding="6" cellspacing="0" width="100%"><tr><th>ID</th><th>Mã</th><th>Họ tên</th><th>Email</th><th>Lớp</th><th>Thao tác</th></tr>
<c:forEach var="sv" items="${dsSinhVien}"><tr><td>${sv.id}</td><td>${sv.maSinhVien}</td><td><a href="${pageContext.request.contextPath}/sinh-vien?action=detail&id=${sv.id}">${sv.hoTen}</a></td><td>${sv.email}</td><td>${sv.lop}</td>
<td><a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}">Sửa</a> | <a href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=${sv.id}" onclick="return confirm('Xóa?')">Xóa</a></td></tr></c:forEach></table>
<p>Trang: <c:forEach begin="1" end="${totalPages}" var="p"><c:choose><c:when test="${p==currentPage}"><b>[${p}]</b></c:when><c:otherwise><a href="${pageContext.request.contextPath}/sinh-vien?page=${p}&keyword=${keyword}">${p}</a></c:otherwise></c:choose> </c:forEach></p>
<a href="${pageContext.request.contextPath}/">Trang chủ</a></body></html>
