package vn.edu.eaut.lab7.filter;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*;
@WebFilter("/admin/*") public class LoginFilter implements Filter{
    public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{HttpServletRequest req=(HttpServletRequest)request;HttpServletResponse resp=(HttpServletResponse)response;HttpSession s=req.getSession(false);if(s==null||s.getAttribute("username")==null){resp.sendRedirect(req.getContextPath()+"/login.jsp");return;}chain.doFilter(request,response);}
}
