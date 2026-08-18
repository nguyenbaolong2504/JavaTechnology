package vn.edu.eaut.lab7.controller;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*;
import vn.edu.eaut.lab7.model.SanPham; import vn.edu.eaut.lab7.repository.SanPhamRepository;
@WebServlet("/san-pham") public class SanPhamController extends HttpServlet{
    private final SanPhamRepository repo=new SanPhamRepository();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.setCharacterEncoding("UTF-8");String a=req.getParameter("action");
        if("new".equals(a)){req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req,resp);return;}
        if("edit".equals(a)||"detail".equals(a)){req.setAttribute("sp",repo.findById(Integer.parseInt(req.getParameter("id"))));req.getRequestDispatcher("detail".equals(a)?"/views/sanpham/detail.jsp":"/views/sanpham/form.jsp").forward(req,resp);return;}
        if("delete".equals(a)){repo.delete(Integer.parseInt(req.getParameter("id")));resp.sendRedirect(req.getContextPath()+"/san-pham");return;}
        req.setAttribute("dsSanPham",repo.search(req.getParameter("keyword")));req.setAttribute("keyword",req.getParameter("keyword")==null?"":req.getParameter("keyword"));req.getRequestDispatcher("/views/sanpham/list.jsp").forward(req,resp);}
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.setCharacterEncoding("UTF-8");String id=req.getParameter("id");double gia;int sl;try{gia=Double.parseDouble(req.getParameter("gia"));sl=Integer.parseInt(req.getParameter("soLuong"));}catch(Exception e){req.setAttribute("error","Giá và số lượng phải là số.");req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req,resp);return;}
        if(gia<=0||sl<0){req.setAttribute("error","Giá phải > 0 và số lượng phải >= 0.");req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req,resp);return;}
        SanPham x=new SanPham(id==null||id.isBlank()?0:Integer.parseInt(id),req.getParameter("ma"),req.getParameter("ten"),req.getParameter("moTa"),gia,sl);if(x.getId()==0)repo.add(x);else repo.update(x);resp.sendRedirect(req.getContextPath()+"/san-pham");}
}
