package vn.edu.eaut.lad3;
import javax.swing.*;
import java.awt.*;

public class Bai06LoginForm extends JFrame {

    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboVaiTro;
    private JCheckBox chkHienMatKhau;
    private JButton btnDangNhap;

    public Bai06LoginForm() {
        setTitle("Bài 6 - Form đăng nhập");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTieuDe = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setBounds(105, 20, 260, 30);
        panel.add(lblTieuDe);

        JLabel lblTaiKhoan = new JLabel("Tài khoản:");
        lblTaiKhoan.setBounds(50, 70, 100, 30);
        panel.add(lblTaiKhoan);

        txtTaiKhoan = new JTextField();
        txtTaiKhoan.setBounds(150, 70, 220, 30);
        panel.add(txtTaiKhoan);

        JLabel lblMatKhau = new JLabel("Mật khẩu:");
        lblMatKhau.setBounds(50, 110, 100, 30);
        panel.add(lblMatKhau);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setBounds(150, 110, 220, 30);
        panel.add(txtMatKhau);

        JLabel lblVaiTro = new JLabel("Vai trò:");
        lblVaiTro.setBounds(50, 150, 100, 30);
        panel.add(lblVaiTro);

        cboVaiTro = new JComboBox<>(new String[]{"Admin", "User"});
        cboVaiTro.setBounds(150, 150, 100, 30);
        panel.add(cboVaiTro);

        chkHienMatKhau = new JCheckBox("Hiển thị mật khẩu");
        chkHienMatKhau.setBounds(255, 150, 150, 30);
        panel.add(chkHienMatKhau);

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setBounds(150, 200, 130, 35);
        panel.add(btnDangNhap);

        // Sự kiện hiển thị mật khẩu
        chkHienMatKhau.addActionListener(e -> {
            if (chkHienMatKhau.isSelected()) {
                txtMatKhau.setEchoChar((char) 0);
            } else {
                txtMatKhau.setEchoChar('•');
            }
        });

        // Sự kiện đăng nhập
        btnDangNhap.addActionListener(e -> dangNhap());

        add(panel);
    }

    private void dangNhap() {
        String taiKhoan = txtTaiKhoan.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String vaiTro = cboVaiTro.getSelectedItem().toString();

        // Kiểm tra để trống
        if (taiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập tài khoản!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            txtTaiKhoan.requestFocus();
            return;
        }

        if (matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập mật khẩu!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );
            txtMatKhau.requestFocus();
            return;
        }

        // Kiểm tra tài khoản
        boolean dangNhapDung = false;

        if (taiKhoan.equals("admin")
                && matKhau.equals("123456")
                && vaiTro.equals("Admin")) {
            dangNhapDung = true;
        }

        if (taiKhoan.equals("user")
                && matKhau.equals("123456")
                && vaiTro.equals("User")) {
            dangNhapDung = true;
        }

        if (dangNhapDung) {
            JOptionPane.showMessageDialog(
                    this,
                    "Chào mừng " + taiKhoan + "!\nVai trò: " + vaiTro,
                    "Đăng nhập thành công",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Đăng nhập thất bại!\n"
                            + "Tài khoản, mật khẩu hoặc vai trò không đúng.",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai06LoginForm().setVisible(true);
        });
    }
}
