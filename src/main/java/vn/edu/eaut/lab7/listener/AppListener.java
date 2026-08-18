package vn.edu.eaut.lab7.listener;
import jakarta.servlet.*; import jakarta.servlet.annotation.*; import jakarta.servlet.http.*;
@WebListener public class AppListener implements ServletContextListener,HttpSessionListener{
    public void contextInitialized(ServletContextEvent e){System.out.println("[Lab7] Ứng dụng khởi động");}
    public void contextDestroyed(ServletContextEvent e){System.out.println("[Lab7] Ứng dụng dừng");}
    public void sessionCreated(HttpSessionEvent e){System.out.println("[Lab7] Session tạo: "+e.getSession().getId());}
    public void sessionDestroyed(HttpSessionEvent e){System.out.println("[Lab7] Session hủy: "+e.getSession().getId());}
}
