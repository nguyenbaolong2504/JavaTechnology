package vn.edu.eaut.lab7.controller;
import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*;
@WebServlet("/logout") public class LogoutController extends HttpServlet{protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws IOException{HttpSession s=req.getSession(false);if(s!=null)s.invalidate();resp.sendRedirect(req.getContextPath()+"/login.jsp");}}
