package vn.edu.eaut.lab7.model;
public class CartItem {
    private SanPham sanPham; private int soLuong;
    public CartItem(SanPham sp,int sl){sanPham=sp;soLuong=sl;}
    public SanPham getSanPham(){return sanPham;} public void setSanPham(SanPham v){sanPham=v;}
    public int getSoLuong(){return soLuong;} public void setSoLuong(int v){soLuong=v;}
    public double getThanhTien(){return sanPham.getGia()*soLuong;}
}
