package vn.edu.eaut.lab7.controller;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*;
import vn.edu.eaut.lab7.model.DiemSinhVien; import vn.edu.eaut.lab7.repository.DiemRepository;
@WebServlet("/diem") public class DiemController extends HttpServlet{
    private final DiemRepository repo=new DiemRepository();
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{String a=req.getParameter("action");
        if("new".equals(a)){req.getRequestDispatcher("/views/diem/form.jsp").forward(req,resp);return;}
        if("edit".equals(a)){req.setAttribute("diem",repo.findById(Integer.parseInt(req.getParameter("id"))));req.getRequestDispatcher("/views/diem/form.jsp").forward(req,resp);return;}
        if("delete".equals(a)){repo.delete(Integer.parseInt(req.getParameter("id")));resp.sendRedirect(req.getContextPath()+"/diem");return;}
        req.setAttribute("dsDiem",repo.findAll());req.getRequestDispatcher("/views/diem/list.jsp").forward(req,resp);}
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{req.setCharacterEncoding("UTF-8");String id=req.getParameter("id");double cc,gk,ck;try{cc=Double.parseDouble(req.getParameter("chuyenCan"));gk=Double.parseDouble(req.getParameter("giuaKy"));ck=Double.parseDouble(req.getParameter("cuoiKy"));}catch(Exception e){req.setAttribute("error","Điểm phải là số.");req.getRequestDispatcher("/views/diem/form.jsp").forward(req,resp);return;}
        if(cc<0||cc>10||gk<0||gk>10||ck<0||ck>10){req.setAttribute("error","Điểm phải từ 0 đến 10.");req.getRequestDispatcher("/views/diem/form.jsp").forward(req,resp);return;}
        DiemSinhVien x=new DiemSinhVien(id==null||id.isBlank()?0:Integer.parseInt(id),req.getParameter("maSinhVien"),req.getParameter("hoTen"),cc,gk,ck);if(x.getId()==0)repo.add(x);else repo.update(x);resp.sendRedirect(req.getContextPath()+"/diem");}
}
