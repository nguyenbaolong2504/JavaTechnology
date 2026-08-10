package vn.edu.eaut.lad3;

public class Student {

    private String maSinhVien;
    private String hoTen;
    private double diemTrungBinh;

    public Student() {
    }

    public Student(String maSinhVien, String hoTen, double diemTrungBinh) {
        this.maSinhVien = maSinhVien;
        this.hoTen = hoTen;
        this.diemTrungBinh = diemTrungBinh;
    }

    public String getMaSinhVien() {
        return maSinhVien;
    }

    public void setMaSinhVien(String maSinhVien) {
        this.maSinhVien = maSinhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public double getDiemTrungBinh() {
        return diemTrungBinh;
    }

    public void setDiemTrungBinh(double diemTrungBinh) {
        this.diemTrungBinh = diemTrungBinh;
    }

    // Tự động xếp loại
    public String getXepLoai() {
        if (diemTrungBinh >= 8.5) {
            return "Giỏi";
        } else if (diemTrungBinh >= 7) {
            return "Khá";
        } else if (diemTrungBinh >= 5) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }
}