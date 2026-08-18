package vn.edu.eaut.lab7.repository;
import vn.edu.eaut.lab7.model.SinhVien; import java.util.*; import java.util.stream.*;
public class SinhVienRepository {
    private static final List<SinhVien> data=new ArrayList<>(); private static int autoId=8;
    static{
        data.add(new SinhVien(1,"20240001","Nguyễn Văn An","an@gmail.com","DCCNTT15.10.1"));
        data.add(new SinhVien(2,"20240002","Trần Thị Bình","binh@gmail.com","DCCNTT15.10.2"));
        data.add(new SinhVien(3,"20240003","Lê Minh Châu","chau@gmail.com","DCCNTT15.10.1"));
        data.add(new SinhVien(4,"20240004","Phạm Hoàng Dũng","dung@gmail.com","DCCNTT15.10.3"));
        data.add(new SinhVien(5,"20240005","Vũ Ngọc Hà","ha@gmail.com","DCCNTT15.10.2"));
        data.add(new SinhVien(6,"20240006","Đỗ Gia Huy","huy@gmail.com","DCCNTT15.10.1"));
        data.add(new SinhVien(7,"20240007","Nguyễn Khánh Linh","linh@gmail.com","DCCNTT15.10.3"));
    }
    public List<SinhVien> findAll(){return new ArrayList<>(data);}
    public SinhVien findById(int id){return data.stream().filter(x->x.getId()==id).findFirst().orElse(null);}
    public void add(SinhVien x){x.setId(autoId++);data.add(x);}
    public void update(SinhVien x){SinhVien o=findById(x.getId());if(o!=null){o.setMaSinhVien(x.getMaSinhVien());o.setHoTen(x.getHoTen());o.setEmail(x.getEmail());o.setLop(x.getLop());}}
    public void delete(int id){data.removeIf(x->x.getId()==id);}
    public List<SinhVien> search(String key){if(key==null||key.isBlank())return findAll();String k=key.toLowerCase();return data.stream().filter(x->x.getMaSinhVien().toLowerCase().contains(k)||x.getHoTen().toLowerCase().contains(k)||x.getLop().toLowerCase().contains(k)).collect(Collectors.toList());}
}
