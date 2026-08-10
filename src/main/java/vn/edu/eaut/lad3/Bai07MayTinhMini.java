package vn.edu.eaut.lad3;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Bai07MayTinhMini extends JFrame {

    private JTextField txtSo1;
    private JTextField txtSo2;
    private JTextField txtKetQua;

    private JButton btnCong;
    private JButton btnTru;
    private JButton btnNhan;
    private JButton btnChia;
    private JButton btnClear;

    private JTextArea txtLichSu;

    public Bai07MayTinhMini() {
        setTitle("Bài 7 - Máy tính mini");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTieuDe = new JLabel("MÁY TÍNH MINI");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 22));
        lblTieuDe.setBounds(185, 15, 200, 30);
        panel.add(lblTieuDe);

        JLabel lblSo1 = new JLabel("Số thứ nhất:");
        lblSo1.setBounds(50, 65, 100, 30);
        panel.add(lblSo1);

        txtSo1 = new JTextField();
        txtSo1.setBounds(150, 65, 320, 30);
        panel.add(txtSo1);

        JLabel lblSo2 = new JLabel("Số thứ hai:");
        lblSo2.setBounds(50, 105, 100, 30);
        panel.add(lblSo2);

        txtSo2 = new JTextField();
        txtSo2.setBounds(150, 105, 320, 30);
        panel.add(txtSo2);

        JLabel lblKetQua = new JLabel("Kết quả:");
        lblKetQua.setBounds(50, 145, 100, 30);
        panel.add(lblKetQua);

        txtKetQua = new JTextField();
        txtKetQua.setBounds(150, 145, 320, 30);

        // Không cho sửa kết quả
        txtKetQua.setEditable(false);

        panel.add(txtKetQua);

        btnCong = new JButton("Cộng");
        btnCong.setBounds(30, 200, 90, 35);
        panel.add(btnCong);

        btnTru = new JButton("Trừ");
        btnTru.setBounds(130, 200, 90, 35);
        panel.add(btnTru);

        btnNhan = new JButton("Nhân");
        btnNhan.setBounds(230, 200, 90, 35);
        panel.add(btnNhan);

        btnChia = new JButton("Chia");
        btnChia.setBounds(330, 200, 90, 35);
        panel.add(btnChia);

        btnClear = new JButton("Clear");
        btnClear.setBounds(430, 200, 80, 35);
        panel.add(btnClear);

        JLabel lblLichSu = new JLabel("Lịch sử phép tính:");
        lblLichSu.setBounds(30, 250, 150, 30);
        panel.add(lblLichSu);

        txtLichSu = new JTextArea();
        txtLichSu.setEditable(false);
        txtLichSu.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(txtLichSu);
        scrollPane.setBounds(30, 285, 480, 130);
        panel.add(scrollPane);

        // Các sự kiện
        btnCong.addActionListener(e -> tinhToan("+"));
        btnTru.addActionListener(e -> tinhToan("-"));
        btnNhan.addActionListener(e -> tinhToan("*"));
        btnChia.addActionListener(e -> tinhToan("/"));

        btnClear.addActionListener(e -> clearForm());

        add(panel);
    }

    private void tinhToan(String phepTinh) {
        try {
            double so1 = Double.parseDouble(txtSo1.getText().trim());
            double so2 = Double.parseDouble(txtSo2.getText().trim());

            double ketQua;

            switch (phepTinh) {
                case "+":
                    ketQua = so1 + so2;
                    break;

                case "-":
                    ketQua = so1 - so2;
                    break;

                case "*":
                    ketQua = so1 * so2;
                    break;

                case "/":
                    if (so2 == 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Không thể chia cho 0!",
                                "Lỗi",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    ketQua = so1 / so2;
                    break;

                default:
                    return;
            }

            txtKetQua.setText(String.valueOf(ketQua));

            String lichSu = so1 + " "
                    + phepTinh + " "
                    + so2 + " = "
                    + ketQua;

            txtLichSu.append(lichSu + "\n");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập đúng định dạng số!",
                    "Lỗi nhập dữ liệu",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void clearForm() {
        txtSo1.setText("");
        txtSo2.setText("");
        txtKetQua.setText("");

        txtSo1.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Bai07MayTinhMini().setVisible(true);
        });
    }
}