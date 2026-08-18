package vn.edu.eaut.lab7.controller;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*; import java.io.*; import java.util.*;
import vn.edu.eaut.lab7.model.*; import vn.edu.eaut.lab7.repository.SanPhamRepository;
@WebServlet("/gio-hang") public class GioHangController extends HttpServlet{
    private final SanPhamRepository repo=new SanPhamRepository();
    @SuppressWarnings("unchecked") private Map<Integer,CartItem> cart(HttpSession s){Map<Integer,CartItem> c=(Map<Integer,CartItem>)s.getAttribute("cart");if(c==null){c=new LinkedHashMap<>();s.setAttribute("cart",c);}return c;}
    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{Map<Integer,CartItem> c=cart(req.getSession());String a=req.getParameter("action");
        if("add".equals(a)){int id=Integer.parseInt(req.getParameter("id"));SanPham sp=repo.findById(id);if(sp!=null){CartItem it=c.get(id);if(it==null)c.put(id,new CartItem(sp,1));else it.setSoLuong(it.getSoLuong()+1);}resp.sendRedirect(req.getContextPath()+"/gio-hang");return;}
        if("remove".equals(a)){c.remove(Integer.parseInt(req.getParameter("id")));resp.sendRedirect(req.getContextPath()+"/gio-hang");return;}
        req.setAttribute("cartItems",c.values());req.setAttribute("tongTien",c.values().stream().mapToDouble(CartItem::getThanhTien).sum());req.setAttribute("dsSanPham",repo.findAll());req.getRequestDispatcher("/views/giohang/index.jsp").forward(req,resp);}
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{Map<Integer,CartItem> c=cart(req.getSession());int id=Integer.parseInt(req.getParameter("id")),sl=Integer.parseInt(req.getParameter("soLuong"));if(sl<=0)c.remove(id);else if(c.containsKey(id))c.get(id).setSoLuong(sl);resp.sendRedirect(req.getContextPath()+"/gio-hang");}
}
