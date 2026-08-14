package vn.edu.eaut.lab5.model;

public class User {
    private final String username;
    private final String hoTen;
    private final String vaiTro;

    public User(String username, String hoTen, String vaiTro) {
        this.username = username;
        this.hoTen = hoTen;
        this.vaiTro = vaiTro;
    }

    public String getUsername() { return username; }
    public String getHoTen() { return hoTen; }
    public String getVaiTro() { return vaiTro; }

    public boolean isAdmin() { return "ADMIN".equalsIgnoreCase(vaiTro); }
    public boolean isNhanVien() { return "NHANVIEN".equalsIgnoreCase(vaiTro); }
    public boolean isKeToan() { return "KETOAN".equalsIgnoreCase(vaiTro); }
}
