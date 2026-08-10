package vn.edu.eaut.lad3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class Bai08QuanLySinhVien extends JFrame {

    private JTextField txtMaSinhVien;
    private JTextField txtHoTen;
    private JTextField txtDiemTrungBinh;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnLamMoi;

    private JTable table;
    private DefaultTableModel tableModel;

    private ArrayList<Student> danhSachSinhVien;

    public Bai08QuanLySinhVien() {
        setTitle("Bài 8 - Quản lý sinh viên");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        danhSachSinhVien = new ArrayList<>();

        initComponents();

        // Dữ liệu mẫu
        themDuLieuMau();
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblTieuDe = new JLabel("QUẢN LÝ SINH VIÊN");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setBounds(245, 15, 300, 35);
        panel.add(lblTieuDe);

        // Mã sinh viên
        JLabel lblMaSinhVien = new JLabel("Mã sinh viên:");
        lblMaSinhVien.setBounds(50, 70, 120, 30);
        panel.add(lblMaSinhVien);

        txtMaSinhVien = new JTextField();
        txtMaSinhVien.setBounds(170, 70, 500, 30);
        panel.add(txtMaSinhVien);

        // Họ tên
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setBounds(50, 110, 120, 30);
        panel.add(lblHoTen);

        txtHoTen = new JTextField();
        txtHoTen.setBounds(170, 110, 500, 30);
        panel.add(txtHoTen);

        // Điểm trung bình
        JLabel lblDiem = new JLabel("Điểm trung bình:");
        lblDiem.setBounds(50, 150, 120, 30);
        panel.add(lblDiem);

        txtDiemTrungBinh = new JTextField();
        txtDiemTrungBinh.setBounds(170, 150, 500, 30);
        panel.add(txtDiemTrungBinh);

        // Các nút
        btnThem = new JButton("Thêm");
        btnThem.setBounds(125, 205, 100, 35);
        panel.add(btnThem);

        btnSua = new JButton("Sửa");
        btnSua.setBounds(245, 205, 100, 35);
        panel.add(btnSua);

        btnXoa = new JButton("Xóa");
        btnXoa.setBounds(365, 205, 100, 35);
        panel.add(btnXoa);

        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setBounds(485, 205, 100, 35);
        panel.add(btnLamMoi);

        // JTable
        String[] columns = {
                "STT",
                "Mã sinh viên",
                "Họ tên",
                "Điểm trung bình",
                "Xếp loại"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Không cho sửa trực tiếp trên bảng
                return false;
            }
        };

        table = new JTable(tableModel);

        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 270, 690, 200);
        panel.add(scrollPane);

        // Sự kiện nút
        btnThem.addActionListener(e -> themSinhVien());

        btnSua.addActionListener(e -> suaSinhVien());

        btnXoa.addActionListener(e -> xoaSinhVien());

        btnLamMoi.addActionListener(e -> lamMoi());

        // Khi click vào dòng JTable
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                hienThiSinhVienLenForm();
            }
        });

        add(panel);
    }

    /*
     * Thêm sinh viên
     */
    private void themSinhVien() {

        Student student = layThongTinTuForm();

        if (student == null) {
            return;
        }

        // Kiểm tra trùng mã sinh viên
        for (Student sv : danhSachSinhVien) {
            if (sv.getMaSinhVien().equalsIgnoreCase(student.getMaSinhVien())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Mã sinh viên đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }

        danhSachSinhVien.add(student);

        capNhatBang();

        JOptionPane.showMessageDialog(
                this,
                "Thêm sinh viên thành công!"
        );

        lamMoi();
    }

    /*
     * Sửa sinh viên
     */
    private void suaSinhVien() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sinh viên cần sửa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Student studentMoi = layThongTinTuForm();

        if (studentMoi == null) {
            return;
        }

        // Kiểm tra mã trùng với sinh viên khác
        for (int i = 0; i < danhSachSinhVien.size(); i++) {

            if (i != selectedRow
                    && danhSachSinhVien.get(i)
                    .getMaSinhVien()
                    .equalsIgnoreCase(studentMoi.getMaSinhVien())) {

                JOptionPane.showMessageDialog(
                        this,
                        "Mã sinh viên đã tồn tại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }

        danhSachSinhVien.set(selectedRow, studentMoi);

        capNhatBang();

        JOptionPane.showMessageDialog(
                this,
                "Cập nhật sinh viên thành công!"
        );

        lamMoi();
    }

    /*
     * Xóa sinh viên
     */
    private void xoaSinhVien() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng chọn sinh viên cần xóa!",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String tenSinhVien =
                danhSachSinhVien.get(selectedRow).getHoTen();

        int luaChon = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa sinh viên:\n"
                        + tenSinhVien + "?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (luaChon == JOptionPane.YES_OPTION) {

            danhSachSinhVien.remove(selectedRow);

            capNhatBang();

            lamMoi();

            JOptionPane.showMessageDialog(
                    this,
                    "Xóa sinh viên thành công!"
            );
        }
    }

    /*
     * Lấy dữ liệu từ form
     */
    private Student layThongTinTuForm() {

        String maSinhVien =
                txtMaSinhVien.getText().trim();

        String hoTen =
                txtHoTen.getText().trim();

        String diemText =
                txtDiemTrungBinh.getText().trim();

        // Kiểm tra dữ liệu trống
        if (maSinhVien.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập mã sinh viên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            txtMaSinhVien.requestFocus();

            return null;
        }

        if (hoTen.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập họ tên!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            txtHoTen.requestFocus();

            return null;
        }

        if (diemText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Vui lòng nhập điểm trung bình!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            txtDiemTrungBinh.requestFocus();

            return null;
        }

        try {

            double diem = Double.parseDouble(diemText);

            if (diem < 0 || diem > 10) {

                JOptionPane.showMessageDialog(
                        this,
                        "Điểm trung bình phải từ 0 đến 10!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE
                );

                txtDiemTrungBinh.requestFocus();

                return null;
            }

            return new Student(
                    maSinhVien,
                    hoTen,
                    diem
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Điểm trung bình phải là số!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE
            );

            txtDiemTrungBinh.requestFocus();

            return null;
        }
    }

    /*
     * Hiển thị danh sách lên JTable
     */
    private void capNhatBang() {

        // Xóa dữ liệu cũ trên JTable
        tableModel.setRowCount(0);

        for (int i = 0; i < danhSachSinhVien.size(); i++) {

            Student sv = danhSachSinhVien.get(i);

            Object[] row = {
                    i + 1,
                    sv.getMaSinhVien(),
                    sv.getHoTen(),
                    sv.getDiemTrungBinh(),
                    sv.getXepLoai()
            };

            tableModel.addRow(row);
        }
    }

    /*
     * Click JTable -> đưa dữ liệu lên form
     */
    private void hienThiSinhVienLenForm() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {

            Student sv =
                    danhSachSinhVien.get(selectedRow);

            txtMaSinhVien.setText(
                    sv.getMaSinhVien()
            );

            txtHoTen.setText(
                    sv.getHoTen()
            );

            txtDiemTrungBinh.setText(
                    String.valueOf(sv.getDiemTrungBinh())
            );
        }
    }

    /*
     * Làm mới form
     */
    private void lamMoi() {

        txtMaSinhVien.setText("");
        txtHoTen.setText("");
        txtDiemTrungBinh.setText("");

        table.clearSelection();

        txtMaSinhVien.requestFocus();
    }

    /*
     * Dữ liệu mẫu
     */
    private void themDuLieuMau() {

        danhSachSinhVien.add(
                new Student(
                        "SV001",
                        "Nguyễn Văn An",
                        9.0
                )
        );

        danhSachSinhVien.add(
                new Student(
                        "SV002",
                        "Trần Minh Bình",
                        7.5
                )
        );

        danhSachSinhVien.add(
                new Student(
                        "SV003",
                        "Lê Hoàng Nam",
                        6.0
                )
        );

        danhSachSinhVien.add(
                new Student(
                        "SV004",
                        "Phạm Văn Long",
                        4.5
                )
        );

        capNhatBang();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new Bai08QuanLySinhVien()
                    .setVisible(true);

        });
    }
}
