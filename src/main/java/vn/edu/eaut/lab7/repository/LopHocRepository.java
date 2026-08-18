package vn.edu.eaut.lab7.repository;
import vn.edu.eaut.lab7.model.LopHoc; import java.util.*; import java.util.stream.*;
public class LopHocRepository{
    private static final List<LopHoc> data=new ArrayList<>(); private static int autoId=4;
    static{data.add(new LopHoc(1,"CNTT01","Công nghệ thông tin 1","Nguyễn Thị Lan",35));data.add(new LopHoc(2,"CNTT02","Công nghệ thông tin 2","Trần Minh Đức",38));data.add(new LopHoc(3,"CNTT03","Công nghệ thông tin 3","Phạm Quang Huy",32));}
    public List<LopHoc> findAll(){return new ArrayList<>(data);} public LopHoc findById(int id){return data.stream().filter(x->x.getId()==id).findFirst().orElse(null);}
    public void add(LopHoc x){x.setId(autoId++);data.add(x);} public void delete(int id){data.removeIf(x->x.getId()==id);}
    public void update(LopHoc x){LopHoc o=findById(x.getId());if(o!=null){o.setMaLop(x.getMaLop());o.setTenLop(x.getTenLop());o.setCoVanHocTap(x.getCoVanHocTap());o.setSoLuongSinhVien(x.getSoLuongSinhVien());}}
    public List<LopHoc> search(String k){if(k==null||k.isBlank())return findAll();k=k.toLowerCase();final String q=k;return data.stream().filter(x->x.getMaLop().toLowerCase().contains(q)||x.getTenLop().toLowerCase().contains(q)).collect(Collectors.toList());}
}
