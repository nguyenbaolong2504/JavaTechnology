package vn.edu.eaut.lab7.repository;
import vn.edu.eaut.lab7.model.SanPham; import java.util.*; import java.util.stream.*;
public class SanPhamRepository{
    private static final List<SanPham> data=new ArrayList<>(); private static int autoId=6;
    static{data.add(new SanPham(1,"SP001","Bàn phím","Bàn phím cơ",650000,20));data.add(new SanPham(2,"SP002","Chuột","Chuột không dây",350000,30));data.add(new SanPham(3,"SP003","Tai nghe","Tai nghe gaming",890000,15));data.add(new SanPham(4,"SP004","Webcam","Webcam Full HD",990000,12));data.add(new SanPham(5,"SP005","USB","USB 64GB",180000,40));}
    public List<SanPham> findAll(){return new ArrayList<>(data);} public SanPham findById(int id){return data.stream().filter(x->x.getId()==id).findFirst().orElse(null);}
    public void add(SanPham x){x.setId(autoId++);data.add(x);} public void delete(int id){data.removeIf(x->x.getId()==id);}
    public void update(SanPham x){SanPham o=findById(x.getId());if(o!=null){o.setMa(x.getMa());o.setTen(x.getTen());o.setMoTa(x.getMoTa());o.setGia(x.getGia());o.setSoLuong(x.getSoLuong());}}
    public List<SanPham> search(String k){if(k==null||k.isBlank())return findAll();k=k.toLowerCase();final String q=k;return data.stream().filter(x->x.getMa().toLowerCase().contains(q)||x.getTen().toLowerCase().contains(q)).collect(Collectors.toList());}
}
