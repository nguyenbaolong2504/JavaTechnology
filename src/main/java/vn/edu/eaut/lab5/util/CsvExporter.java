package vn.edu.eaut.lab5.util;

import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.KhachHang;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;

public final class CsvExporter {
    private CsvExporter() {}

    public static Path exportInvoice(int maHd, KhachHang kh, List<ChiTietHoaDon> items) throws IOException {
        Path outDir = Paths.get("exports");
        Files.createDirectories(outDir);
        Path file = outDir.resolve("HoaDon_" + maHd + ".csv");

        StringBuilder sb = new StringBuilder();
        sb.append("Ma hoa don,").append(maHd).append('\n');
        sb.append("Ngay lap,").append(LocalDate.now()).append('\n');
        sb.append("Khach hang,").append(csv(kh.getTenKh())).append('\n');
        sb.append("So dien thoai,").append(csv(kh.getSdt())).append('\n');
        sb.append('\n');
        sb.append("Ten san pham,So luong,Don gia,Thanh tien\n");

        BigDecimal total = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : items) {
            sb.append(csv(ct.getTenSp())).append(',')
              .append(ct.getSoLuong()).append(',')
              .append(ct.getDonGia()).append(',')
              .append(ct.getThanhTien()).append('\n');
            total = total.add(ct.getThanhTien());
        }
        sb.append("Tong tien,,,").append(total).append('\n');

        Files.writeString(file, "\uFEFF" + sb, StandardCharsets.UTF_8);
        return file.toAbsolutePath();
    }

    private static String csv(String value) {
        if (value == null) return "";
        return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
