package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDAL {
    public int insertHoaDon(int maKh, List<ChiTietHoaDon> list) throws SQLException {
        if (list == null || list.isEmpty()) throw new IllegalArgumentException("Hoa don chua co san pham.");

        String insertHd = "INSERT INTO hoa_don(ngay_lap, ma_kh, tong_tien) VALUES(?,?,?)";
        String lockSp = "SELECT so_luong, don_gia FROM san_pham WHERE ma_sp=? FOR UPDATE";
        String insertCt = "INSERT INTO chi_tiet_hoa_don(ma_hd,ma_sp,so_luong,don_gia,thanh_tien) VALUES(?,?,?,?,?)";
        String updateKho = "UPDATE san_pham SET so_luong=so_luong-? WHERE ma_sp=?";

        Connection c = null;
        try {
            c = DBHelper.getConnection();
            c.setAutoCommit(false);

            for (ChiTietHoaDon ct : list) {
                try (PreparedStatement ps = c.prepareStatement(lockSp)) {
                    ps.setInt(1, ct.getMaSp());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new SQLException("San pham khong ton tai: " + ct.getTenSp());
                        int ton = rs.getInt("so_luong");
                        if (ct.getSoLuong() <= 0) throw new IllegalArgumentException("So luong phai lon hon 0.");
                        if (ct.getSoLuong() > ton) {
                            throw new IllegalStateException("San pham " + ct.getTenSp() + " chi con " + ton + " san pham.");
                        }
                    }
                }
            }

            BigDecimal tong = list.stream()
                    .map(ChiTietHoaDon::getThanhTien)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            int maHd;
            try (PreparedStatement ps = c.prepareStatement(insertHd, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setInt(2, maKh);
                ps.setBigDecimal(3, tong);
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) throw new SQLException("Khong lay duoc ma hoa don.");
                    maHd = rs.getInt(1);
                }
            }

            try (PreparedStatement psCt = c.prepareStatement(insertCt);
                 PreparedStatement psKho = c.prepareStatement(updateKho)) {
                for (ChiTietHoaDon ct : list) {
                    psCt.setInt(1, maHd);
                    psCt.setInt(2, ct.getMaSp());
                    psCt.setInt(3, ct.getSoLuong());
                    psCt.setBigDecimal(4, ct.getDonGia());
                    psCt.setBigDecimal(5, ct.getThanhTien());
                    psCt.addBatch();

                    psKho.setInt(1, ct.getSoLuong());
                    psKho.setInt(2, ct.getMaSp());
                    psKho.addBatch();
                }
                psCt.executeBatch();
                psKho.executeBatch();
            }

            c.commit();
            return maHd;
        } catch (SQLException | RuntimeException e) {
            if (c != null) c.rollback();
            throw e;
        } finally {
            if (c != null) {
                try { c.setAutoCommit(true); } catch (SQLException ignored) {}
                c.close();
            }
        }
    }

    public List<HoaDon> search(LocalDate from, LocalDate to, Integer maKh) throws SQLException {
        List<HoaDon> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("""
            SELECT hd.ma_hd, hd.ngay_lap, hd.ma_kh, kh.ten_kh, hd.tong_tien
            FROM hoa_don hd JOIN khach_hang kh ON hd.ma_kh=kh.ma_kh
            WHERE hd.ngay_lap BETWEEN ? AND ?
        """);
        if (maKh != null) sql.append(" AND hd.ma_kh=?");
        sql.append(" ORDER BY hd.ma_hd DESC");

        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            ps.setDate(1, Date.valueOf(from));
            ps.setDate(2, Date.valueOf(to));
            if (maKh != null) ps.setInt(3, maKh);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon hd = new HoaDon();
                    hd.setMaHd(rs.getInt("ma_hd"));
                    hd.setNgayLap(rs.getDate("ngay_lap").toLocalDate());
                    hd.setMaKh(rs.getInt("ma_kh"));
                    hd.setTenKh(rs.getString("ten_kh"));
                    hd.setTongTien(rs.getBigDecimal("tong_tien"));
                    list.add(hd);
                }
            }
        }
        return list;
    }
}
