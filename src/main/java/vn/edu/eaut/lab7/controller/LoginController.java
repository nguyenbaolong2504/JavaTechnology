package vn.edu.eaut.lab7.controller;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*;
@WebServlet("/login") public class LoginController extends HttpServlet{
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.setCharacterEncoding("UTF-8");String u=req.getParameter("username"),p=req.getParameter("password");if("admin".equals(u)&&"123456".equals(p)){req.getSession().setAttribute("username",u);resp.sendRedirect(req.getContextPath()+"/admin/");}else{req.setAttribute("error","Sai tài khoản hoặc mật khẩu.");req.getRequestDispatcher("/login.jsp").forward(req,resp);}}
}
