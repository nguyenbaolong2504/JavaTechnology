package vn.edu.eaut.lab7.repository;
import vn.edu.eaut.lab7.model.DiemSinhVien; import java.util.*;
public class DiemRepository{
    private static final List<DiemSinhVien> data=new ArrayList<>(); private static int autoId=3;
    static{data.add(new DiemSinhVien(1,"20240001","Nguyễn Văn An",9,8,8.5));data.add(new DiemSinhVien(2,"20240002","Trần Thị Bình",8,7,7.5));}
    public List<DiemSinhVien> findAll(){return new ArrayList<>(data);} public DiemSinhVien findById(int id){return data.stream().filter(x->x.getId()==id).findFirst().orElse(null);}
    public void add(DiemSinhVien x){x.setId(autoId++);data.add(x);} public void delete(int id){data.removeIf(x->x.getId()==id);}
    public void update(DiemSinhVien x){DiemSinhVien o=findById(x.getId());if(o!=null){o.setMaSinhVien(x.getMaSinhVien());o.setHoTen(x.getHoTen());o.setChuyenCan(x.getChuyenCan());o.setGiuaKy(x.getGiuaKy());o.setCuoiKy(x.getCuoiKy());}}
}
