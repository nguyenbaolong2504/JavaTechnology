package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.DanhMuc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAL {
    public List<DanhMuc> findAll() throws SQLException {
        List<DanhMuc> list = new ArrayList<>();
        String sql = "SELECT ma_dm, ten_dm FROM danh_muc ORDER BY ten_dm";
        try (Connection c = DBHelper.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new DanhMuc(rs.getInt(1), rs.getString(2)));
        }
        return list;
    }

    public boolean insert(DanhMuc dm) throws SQLException {
        String sql = "INSERT INTO danh_muc(ten_dm) VALUES(?)";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dm.getTenDm());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(DanhMuc dm) throws SQLException {
        String sql = "UPDATE danh_muc SET ten_dm=? WHERE ma_dm=?";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, dm.getTenDm());
            ps.setInt(2, dm.getMaDm());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean hasProducts(int maDm) throws SQLException {
        try (Connection c = DBHelper.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM san_pham WHERE ma_dm=?")) {
            ps.setInt(1, maDm);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public boolean delete(int maDm) throws SQLException {
        if (hasProducts(maDm)) throw new IllegalStateException("Danh muc dang co san pham, khong the xoa.");
        try (Connection c = DBHelper.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM danh_muc WHERE ma_dm=?")) {
            ps.setInt(1, maDm);
            return ps.executeUpdate() > 0;
        }
    }
}
