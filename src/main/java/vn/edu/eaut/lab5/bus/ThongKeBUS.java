package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeBUS {
    private final ThongKeDAL dal = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate from, LocalDate to) throws SQLException {
        if (from == null || to == null || from.isAfter(to))
            throw new IllegalArgumentException("Khoang ngay khong hop le.");
        return dal.tinhDoanhThu(from, to);
    }

    public String hoaDonCaoNhat() throws SQLException { return dal.hoaDonCaoNhat(); }
    public String sanPhamBanChay() throws SQLException { return dal.sanPhamBanChay(); }
}
