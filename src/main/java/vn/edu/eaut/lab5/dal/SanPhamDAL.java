package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.SanPham;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamDAL {
    private SanPham map(ResultSet rs) throws SQLException {
        SanPham sp = new SanPham();
        sp.setMaSp(rs.getInt("ma_sp"));
        sp.setTenSp(rs.getString("ten_sp"));
        sp.setDonGia(rs.getBigDecimal("don_gia"));
        sp.setSoLuong(rs.getInt("so_luong"));
        int dm = rs.getInt("ma_dm");
        sp.setMaDm(rs.wasNull() ? null : dm);
        sp.setTenDm(rs.getString("ten_dm"));
        return sp;
    }

    public List<SanPham> findAll() throws SQLException {
        return searchAdvanced("", null, null, null, null, 1, 1000);
    }

    public List<SanPham> searchByName(String keyword) throws SQLException {
        return searchAdvanced(keyword, null, null, null, null, 1, 1000);
    }

    public List<SanPham> searchAdvanced(String keyword, BigDecimal minGia, BigDecimal maxGia,
                                        Integer minSl, Integer maDm, int page, int pageSize) throws SQLException {
        List<SanPham> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT sp.ma_sp, sp.ten_sp, sp.don_gia, sp.so_luong, sp.ma_dm, dm.ten_dm
            FROM san_pham sp
            LEFT JOIN danh_muc dm ON sp.ma_dm = dm.ma_dm
            WHERE sp.ten_sp LIKE ?
        """);
        if (minGia != null) sql.append(" AND sp.don_gia >= ?");
        if (maxGia != null) sql.append(" AND sp.don_gia <= ?");
        if (minSl != null) sql.append(" AND sp.so_luong >= ?");
        if (maDm != null) sql.append(" AND sp.ma_dm = ?");
        sql.append(" ORDER BY sp.ma_sp DESC LIMIT ? OFFSET ?");

        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int i = 1;
            ps.setString(i++, "%" + (keyword == null ? "" : keyword.trim()) + "%");
            if (minGia != null) ps.setBigDecimal(i++, minGia);
            if (maxGia != null) ps.setBigDecimal(i++, maxGia);
            if (minSl != null) ps.setInt(i++, minSl);
            if (maDm != null) ps.setInt(i++, maDm);
            ps.setInt(i++, pageSize);
            ps.setInt(i, Math.max(0, (page - 1) * pageSize));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        }
        return list;
    }

    public boolean insert(SanPham sp) throws SQLException {
        String sql = "INSERT INTO san_pham(ten_sp, don_gia, so_luong, ma_dm) VALUES(?,?,?,?)";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, sp);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean update(SanPham sp) throws SQLException {
        String sql = "UPDATE san_pham SET ten_sp=?, don_gia=?, so_luong=?, ma_dm=? WHERE ma_sp=?";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            fill(ps, sp);
            ps.setInt(5, sp.getMaSp());
            return ps.executeUpdate() > 0;
        }
    }

    private void fill(PreparedStatement ps, SanPham sp) throws SQLException {
        ps.setString(1, sp.getTenSp());
        ps.setBigDecimal(2, sp.getDonGia());
        ps.setInt(3, sp.getSoLuong());
        if (sp.getMaDm() == null) ps.setNull(4, Types.INTEGER);
        else ps.setInt(4, sp.getMaDm());
    }

    public boolean delete(int maSp) throws SQLException {
        try (Connection c = DBHelper.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM san_pham WHERE ma_sp=?")) {
            ps.setInt(1, maSp);
            return ps.executeUpdate() > 0;
        }
    }
}
