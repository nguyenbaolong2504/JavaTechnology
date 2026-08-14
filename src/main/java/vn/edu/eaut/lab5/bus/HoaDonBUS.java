package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class HoaDonBUS {
    private final HoaDonDAL dal = new HoaDonDAL();

    public int create(int maKh, List<ChiTietHoaDon> list) throws SQLException {
        if (maKh <= 0) throw new IllegalArgumentException("Vui long chon khach hang.");
        return dal.insertHoaDon(maKh, list);
    }

    public List<HoaDon> search(LocalDate from, LocalDate to, Integer maKh) throws SQLException {
        if (from == null || to == null || from.isAfter(to))
            throw new IllegalArgumentException("Khoang ngay khong hop le.");
        return dal.search(from, to, maKh);
    }
}
