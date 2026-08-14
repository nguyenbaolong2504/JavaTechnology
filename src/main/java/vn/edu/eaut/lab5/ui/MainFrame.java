package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.model.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private final User user;

    public MainFrame(User user) {
        this.user = user;
        setTitle("MiniShop - " + user.getHoTen() + " (" + user.getVaiTro() + ")");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1180, 760);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        if (!user.isKeToan()) {
            tabs.addTab("San pham", new SanPhamPanel());
            tabs.addTab("Khach hang", new KhachHangPanel());
            tabs.addTab("Hoa don", new HoaDonPanel());
        }
        if (user.isAdmin()) {
            tabs.addTab("Danh muc", new DanhMucPanel());
        }
        if (user.isAdmin() || user.isKeToan()) {
            tabs.addTab("Thong ke", new ThongKePanel());
        }

        JButton logout = new JButton("Dang xuat");
        logout.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        top.add(new JLabel("Xin chao: " + user.getHoTen() + " | Vai tro: " + user.getVaiTro()), BorderLayout.WEST);
        top.add(logout, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }
}
