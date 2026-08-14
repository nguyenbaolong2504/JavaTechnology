package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class ThongKeDAL {
    public BigDecimal tinhDoanhThu(LocalDate from, LocalDate to) throws SQLException {
        String sql = "SELECT COALESCE(SUM(tong_tien),0) FROM hoa_don WHERE ngay_lap BETWEEN ? AND ?";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getBigDecimal(1);
            }
        }
    }

    public String hoaDonCaoNhat() throws SQLException {
        String sql = """
            SELECT hd.ma_hd, hd.ngay_lap, kh.ten_kh, hd.tong_tien
            FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh=kh.ma_kh
            ORDER BY hd.tong_tien DESC LIMIT 1
        """;
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return "Chua co hoa don.";
            return "HD #" + rs.getInt(1) + " | " + rs.getDate(2) + " | " + rs.getString(3) + " | " + rs.getBigDecimal(4) + " VND";
        }
    }

    public String sanPhamBanChay() throws SQLException {
        String sql = """
            SELECT sp.ten_sp, SUM(ct.so_luong) tong
            FROM chi_tiet_hoa_don ct JOIN san_pham sp ON ct.ma_sp=sp.ma_sp
            GROUP BY sp.ma_sp, sp.ten_sp
            ORDER BY tong DESC LIMIT 1
        """;
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (!rs.next()) return "Chua co du lieu ban hang.";
            return rs.getString(1) + " - da ban " + rs.getInt(2);
        }
    }
}
