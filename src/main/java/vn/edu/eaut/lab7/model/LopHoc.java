package vn.edu.eaut.lab7.model;
public class LopHoc {
    private int id, soLuongSinhVien; private String maLop, tenLop, coVanHocTap;
    public LopHoc() {}
    public LopHoc(int id,String maLop,String tenLop,String coVanHocTap,int soLuongSinhVien){
        this.id=id;this.maLop=maLop;this.tenLop=tenLop;this.coVanHocTap=coVanHocTap;this.soLuongSinhVien=soLuongSinhVien;
    }
    public int getId(){return id;} public void setId(int v){id=v;}
    public String getMaLop(){return maLop;} public void setMaLop(String v){maLop=v;}
    public String getTenLop(){return tenLop;} public void setTenLop(String v){tenLop=v;}
    public String getCoVanHocTap(){return coVanHocTap;} public void setCoVanHocTap(String v){coVanHocTap=v;}
    public int getSoLuongSinhVien(){return soLuongSinhVien;} public void setSoLuongSinhVien(int v){soLuongSinhVien=v;}
}
