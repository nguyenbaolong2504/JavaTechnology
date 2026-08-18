package vn.edu.eaut.lab7.repository;
import vn.edu.eaut.lab7.model.Sach; import java.util.*; import java.util.stream.*;
public class SachRepository{
    private static final List<Sach> data=new ArrayList<>(); private static int autoId=4;
    static{data.add(new Sach(1,"S001","Lập trình Java","Nguyễn Văn A","Giáo dục",2024));data.add(new Sach(2,"S002","Cấu trúc dữ liệu","Trần Văn B","Khoa học",2023));data.add(new Sach(3,"S003","Cơ sở dữ liệu","Lê Văn C","Thông tin",2025));}
    public List<Sach> findAll(){return new ArrayList<>(data);} public Sach findById(int id){return data.stream().filter(x->x.getId()==id).findFirst().orElse(null);}
    public void add(Sach x){x.setId(autoId++);data.add(x);} public void delete(int id){data.removeIf(x->x.getId()==id);}
    public void update(Sach x){Sach o=findById(x.getId());if(o!=null){o.setMaSach(x.getMaSach());o.setTenSach(x.getTenSach());o.setTacGia(x.getTacGia());o.setNhaXuatBan(x.getNhaXuatBan());o.setNamXuatBan(x.getNamXuatBan());}}
    public List<Sach> search(String k){if(k==null||k.isBlank())return findAll();k=k.toLowerCase();final String q=k;return data.stream().filter(x->x.getTenSach().toLowerCase().contains(q)||x.getTacGia().toLowerCase().contains(q)).collect(Collectors.toList());}
}
