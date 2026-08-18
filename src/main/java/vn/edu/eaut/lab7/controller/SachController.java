package vn.edu.eaut.lab7.controller;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*;
import vn.edu.eaut.lab7.model.Sach; import vn.edu.eaut.lab7.repository.SachRepository;
@WebServlet("/sach") public class SachController extends HttpServlet{
    private final SachRepository repo=new SachRepository();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");String a=req.getParameter("action");
        if("new".equals(a)){req.getRequestDispatcher("/views/sach/form.jsp").forward(req,resp);return;}
        if("edit".equals(a)||"detail".equals(a)){req.setAttribute("sach",repo.findById(Integer.parseInt(req.getParameter("id"))));req.getRequestDispatcher("detail".equals(a)?"/views/sach/detail.jsp":"/views/sach/form.jsp").forward(req,resp);return;}
        if("delete".equals(a)){repo.delete(Integer.parseInt(req.getParameter("id")));resp.sendRedirect(req.getContextPath()+"/sach");return;}
        req.setAttribute("dsSach",repo.search(req.getParameter("keyword")));req.setAttribute("keyword",req.getParameter("keyword")==null?"":req.getParameter("keyword"));req.getRequestDispatcher("/views/sach/list.jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{req.setCharacterEncoding("UTF-8");String id=req.getParameter("id");int nam=0;try{nam=Integer.parseInt(req.getParameter("namXuatBan"));}catch(Exception ignored){}
        Sach x=new Sach(id==null||id.isBlank()?0:Integer.parseInt(id),req.getParameter("maSach"),req.getParameter("tenSach"),req.getParameter("tacGia"),req.getParameter("nhaXuatBan"),nam);if(x.getId()==0)repo.add(x);else repo.update(x);resp.sendRedirect(req.getContextPath()+"/sach");}
}
