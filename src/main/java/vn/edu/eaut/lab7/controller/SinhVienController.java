package vn.edu.eaut.lab7.controller;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*; import java.util.*;
import vn.edu.eaut.lab7.model.SinhVien; import vn.edu.eaut.lab7.repository.SinhVienRepository;
@WebServlet("/sinh-vien")
public class SinhVienController extends HttpServlet{
    private final SinhVienRepository repo=new SinhVienRepository(); private static final int PAGE_SIZE=5;
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8"); String a=req.getParameter("action");
        if("new".equals(a)){req.getRequestDispatcher("/views/sinhvien/form.jsp").forward(req,resp);return;}
        if("edit".equals(a)||"detail".equals(a)){req.setAttribute("sv",repo.findById(Integer.parseInt(req.getParameter("id"))));req.getRequestDispatcher("detail".equals(a)?"/views/sinhvien/detail.jsp":"/views/sinhvien/form.jsp").forward(req,resp);return;}
        if("delete".equals(a)){repo.delete(Integer.parseInt(req.getParameter("id")));resp.sendRedirect(req.getContextPath()+"/sinh-vien");return;}
        String k=req.getParameter("keyword"); List<SinhVien> all=repo.search(k); int page=1; try{page=Math.max(1,Integer.parseInt(req.getParameter("page")));}catch(Exception ignored){}
        int totalPages=Math.max(1,(int)Math.ceil(all.size()/(double)PAGE_SIZE)); if(page>totalPages)page=totalPages; int from=Math.min((page-1)*PAGE_SIZE,all.size()),to=Math.min(from+PAGE_SIZE,all.size());
        req.setAttribute("dsSinhVien",all.subList(from,to));req.setAttribute("keyword",k==null?"":k);req.setAttribute("currentPage",page);req.setAttribute("totalPages",totalPages);
        req.getRequestDispatcher("/views/sinhvien/list.jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8"); String id=req.getParameter("id"),ma=req.getParameter("maSinhVien"),ten=req.getParameter("hoTen");
        if(ma==null||ma.isBlank()||ten==null||ten.isBlank()){req.setAttribute("error","Mã sinh viên và họ tên là bắt buộc.");req.getRequestDispatcher("/views/sinhvien/form.jsp").forward(req,resp);return;}
        SinhVien x=new SinhVien(id==null||id.isBlank()?0:Integer.parseInt(id),ma.trim(),ten.trim(),req.getParameter("email"),req.getParameter("lop"));
        if(x.getId()==0)repo.add(x);else repo.update(x);resp.sendRedirect(req.getContextPath()+"/sinh-vien");
    }
}
