package vn.edu.eaut.lab7.model;
public class DiemSinhVien {
    private int id; private String maSinhVien, hoTen; private double chuyenCan, giuaKy, cuoiKy;
    public DiemSinhVien() {}
    public DiemSinhVien(int id,String ma,String ten,double cc,double gk,double ck){
        this.id=id;maSinhVien=ma;hoTen=ten;chuyenCan=cc;giuaKy=gk;cuoiKy=ck;
    }
    public double getTongKet(){return Math.round((chuyenCan*0.1+giuaKy*0.3+cuoiKy*0.6)*100.0)/100.0;}
    public String getXepLoai(){double d=getTongKet(); if(d>=8.5)return "A"; if(d>=7)return "B"; if(d>=5.5)return "C"; if(d>=4)return "D"; return "F";}
    public int getId(){return id;} public void setId(int v){id=v;}
    public String getMaSinhVien(){return maSinhVien;} public void setMaSinhVien(String v){maSinhVien=v;}
    public String getHoTen(){return hoTen;} public void setHoTen(String v){hoTen=v;}
    public double getChuyenCan(){return chuyenCan;} public void setChuyenCan(double v){chuyenCan=v;}
    public double getGiuaKy(){return giuaKy;} public void setGiuaKy(double v){giuaKy=v;}
    public double getCuoiKy(){return cuoiKy;} public void setCuoiKy(double v){cuoiKy=v;}
}
