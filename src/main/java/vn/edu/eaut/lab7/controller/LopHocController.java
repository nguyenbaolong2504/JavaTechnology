package vn.edu.eaut.lab7.controller;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*;
import vn.edu.eaut.lab7.model.LopHoc; import vn.edu.eaut.lab7.repository.LopHocRepository;
@WebServlet("/lop-hoc") public class LopHocController extends HttpServlet{
    private final LopHocRepository repo=new LopHocRepository();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{String a=req.getParameter("action");
        if("new".equals(a)){req.getRequestDispatcher("/views/lophoc/form.jsp").forward(req,resp);return;}
        if("edit".equals(a)){req.setAttribute("lop",repo.findById(Integer.parseInt(req.getParameter("id"))));req.getRequestDispatcher("/views/lophoc/form.jsp").forward(req,resp);return;}
        if("delete".equals(a)){repo.delete(Integer.parseInt(req.getParameter("id")));resp.sendRedirect(req.getContextPath()+"/lop-hoc");return;}
        req.setAttribute("dsLop",repo.search(req.getParameter("keyword")));req.setAttribute("keyword",req.getParameter("keyword")==null?"":req.getParameter("keyword"));req.getRequestDispatcher("/views/lophoc/list.jsp").forward(req,resp);}
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{req.setCharacterEncoding("UTF-8");String id=req.getParameter("id");LopHoc x=new LopHoc(id==null||id.isBlank()?0:Integer.parseInt(id),req.getParameter("maLop"),req.getParameter("tenLop"),req.getParameter("coVanHocTap"),Integer.parseInt(req.getParameter("soLuongSinhVien")));if(x.getId()==0)repo.add(x);else repo.update(x);resp.sendRedirect(req.getContextPath()+"/lop-hoc");}
}
