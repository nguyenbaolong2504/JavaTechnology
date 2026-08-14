package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.*;
import vn.edu.eaut.lab5.util.CsvExporter;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HoaDonPanel extends JPanel {
    private final JComboBox<KhachHang> cboKh = new JComboBox<>();
    private final JComboBox<SanPham> cboSp = new JComboBox<>();
    private final JTextField txtSoLuong = new JTextField("1",5);
    private final JLabel lblTong = new JLabel("Tong tien: 0 VND");

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Ma SP","Ten SP","So luong","Don gia","Thanh tien"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable table = new JTable(model);
    private final List<ChiTietHoaDon> cart = new ArrayList<>();

    private final KhachHangBUS khBus = new KhachHangBUS();
    private final SanPhamBUS spBus = new SanPhamBUS();
    private final HoaDonBUS hdBus = new HoaDonBUS();

    public HoaDonPanel() {
        setLayout(new BorderLayout(8,8));

        JPanel top = new JPanel();
        top.add(new JLabel("Khach hang:")); top.add(cboKh);
        top.add(new JLabel("San pham:")); top.add(cboSp);
        top.add(new JLabel("So luong:")); top.add(txtSoLuong);

        JButton add = new JButton("Them dong");
        JButton remove = new JButton("Xoa dong");
        JButton save = new JButton("Luu hoa don");
        JButton reload = new JButton("Tai lai du lieu");
        top.add(add);top.add(remove);top.add(save);top.add(reload);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblTong.setFont(lblTong.getFont().deriveFont(Font.BOLD,16f));
        bottom.add(lblTong);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);

        add.addActionListener(e->addItem());
        remove.addActionListener(e->removeItem());
        save.addActionListener(e->saveInvoice());
        reload.addActionListener(e->loadCombos());

        loadCombos();
    }

    private void loadCombos() {
        try {
            cboKh.removeAllItems();
            for(KhachHang kh:khBus.findAll()) cboKh.addItem(kh);
            cboSp.removeAllItems();
            for(SanPham sp:spBus.findAll()) cboSp.addItem(sp);
        } catch(Exception e){MessageUtil.error(this,e);}
    }

    private void addItem() {
        try {
            SanPham sp=(SanPham)cboSp.getSelectedItem();
            if(sp==null) throw new IllegalArgumentException("Khong co san pham.");
            int sl=Integer.parseInt(txtSoLuong.getText().trim());
            if(sl<=0) throw new IllegalArgumentException("So luong phai lon hon 0.");
            if(sp.getSoLuong()<=0) throw new IllegalStateException("San pham da het hang.");
            if(sl>sp.getSoLuong()) throw new IllegalStateException("Ton kho chi con "+sp.getSoLuong()+".");

            for(ChiTietHoaDon ct:cart){
                if(ct.getMaSp()==sp.getMaSp()){
                    int newSl=ct.getSoLuong()+sl;
                    if(newSl>sp.getSoLuong()) throw new IllegalStateException("Tong so luong vuot ton kho.");
                    ct.setSoLuong(newSl);
                    refreshCart();
                    return;
                }
            }

            cart.add(new ChiTietHoaDon(sp.getMaSp(),sp.getTenSp(),sl,sp.getDonGia()));
            refreshCart();
        } catch(NumberFormatException e){
            MessageUtil.error(this,new IllegalArgumentException("So luong phai la so nguyen."));
        } catch(Exception e){MessageUtil.error(this,e);}
    }

    private void removeItem() {
        int r=table.getSelectedRow();
        if(r>=0){cart.remove(r);refreshCart();}
    }

    private void refreshCart() {
        model.setRowCount(0);
        BigDecimal total=BigDecimal.ZERO;
        for(ChiTietHoaDon ct:cart){
            model.addRow(new Object[]{ct.getMaSp(),ct.getTenSp(),ct.getSoLuong(),ct.getDonGia(),ct.getThanhTien()});
            total=total.add(ct.getThanhTien());
        }
        lblTong.setText("Tong tien: "+total+" VND");
    }

    private void saveInvoice() {
        try {
            KhachHang kh=(KhachHang)cboKh.getSelectedItem();
            if(kh==null) throw new IllegalArgumentException("Vui long chon khach hang.");
            List<ChiTietHoaDon> snapshot=new ArrayList<>(cart);
            int id=hdBus.create(kh.getMaKh(),snapshot);
            Path file=CsvExporter.exportInvoice(id,kh,snapshot);
            MessageUtil.info(this,"Luu hoa don #"+id+" thanh cong.\nDa xuat CSV: "+file);
            cart.clear();refreshCart();loadCombos();
        } catch(Exception e){MessageUtil.error(this,e);}
    }
}
