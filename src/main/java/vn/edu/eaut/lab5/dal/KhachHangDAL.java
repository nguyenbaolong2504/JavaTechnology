package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.KhachHang;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAL {
    private KhachHang map(ResultSet rs) throws SQLException {
        return new KhachHang(
                rs.getInt("ma_kh"),
                rs.getString("ten_kh"),
                rs.getString("sdt"),
                rs.getString("dia_chi")
        );
    }

    public List<KhachHang> findAll() throws SQLException {
        return search("");
    }

    public List<KhachHang> search(String keyword) throws SQLException {
        List<KhachHang> list = new ArrayList<>();
        String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        String sql = "SELECT * FROM khach_hang WHERE ten_kh LIKE ? OR sdt LIKE ? OR dia_chi LIKE ? ORDER BY ma_kh DESC";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setString(3, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean insert(KhachHang kh) throws SQLException {
        String sql = "INSERT INTO khach_hang(ten_kh,sdt,dia_chi) VALUES(?,?,?)";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, kh);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(KhachHang kh) throws SQLException {
        String sql = "UPDATE khach_hang SET ten_kh=?,sdt=?,dia_chi=? WHERE ma_kh=?";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, kh);
            ps.setInt(4, kh.getMaKh());
            return ps.executeUpdate() > 0;
        }
    }

    private void fill(PreparedStatement ps, KhachHang kh) throws SQLException {
        ps.setString(1, kh.getTenKh());
        ps.setString(2, kh.getSdt());
        ps.setString(3, kh.getDiaChi());
    }

    public boolean delete(int maKh) throws SQLException {
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM khach_hang WHERE ma_kh=?")) {
            ps.setInt(1, maKh);
            return ps.executeUpdate() > 0;
        }
    }
}
