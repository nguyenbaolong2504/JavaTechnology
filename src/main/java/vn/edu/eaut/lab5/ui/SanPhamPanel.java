package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.DanhMuc;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SanPhamPanel extends JPanel {
    private final JTextField txtId = new JTextField(5);
    private final JTextField txtTen = new JTextField(18);
    private final JTextField txtGia = new JTextField(10);
    private final JTextField txtSoLuong = new JTextField(7);
    private final JComboBox<DanhMuc> cboDm = new JComboBox<>();

    private final JTextField txtSearch = new JTextField(16);
    private final JTextField txtMinGia = new JTextField(8);
    private final JTextField txtMaxGia = new JTextField(8);
    private final JTextField txtMinSl = new JTextField(6);
    private final JComboBox<Object> cboFilterDm = new JComboBox<>();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Ma SP","Ten SP","Don gia","So luong","Danh muc","Canh bao"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable table = new JTable(model);

    private final SanPhamBUS bus = new SanPhamBUS();
    private final DanhMucBUS dmBus = new DanhMucBUS();
    private int page = 1;
    private static final int PAGE_SIZE = 10;
    private final JLabel lblPage = new JLabel("Trang 1");

    public SanPhamPanel() {
        setLayout(new BorderLayout(5,5));
        txtId.setEditable(false);

        JPanel north = new JPanel();
        north.add(new JLabel("Ma:")); north.add(txtId);
        north.add(new JLabel("Ten:")); north.add(txtTen);
        north.add(new JLabel("Gia:")); north.add(txtGia);
        north.add(new JLabel("SL:")); north.add(txtSoLuong);
        north.add(new JLabel("Danh muc:")); north.add(cboDm);

        JButton save = new JButton("Them / Luu");
        JButton del = new JButton("Xoa");
        JButton clear = new JButton("Lam moi");
        north.add(save); north.add(del); north.add(clear);

        JPanel filters = new JPanel();
        filters.add(new JLabel("Tim ten:")); filters.add(txtSearch);
        filters.add(new JLabel("Gia tu:")); filters.add(txtMinGia);
        filters.add(new JLabel("den:")); filters.add(txtMaxGia);
        filters.add(new JLabel("SL >=")); filters.add(txtMinSl);
        filters.add(new JLabel("DM:")); filters.add(cboFilterDm);
        JButton search = new JButton("Tim");
        JButton prev = new JButton("Truoc");
        JButton next = new JButton("Sau");
        filters.add(search); filters.add(prev); filters.add(lblPage); filters.add(next);

        JPanel top = new JPanel(new BorderLayout());
        top.add(north, BorderLayout.NORTH);
        top.add(filters, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadCategories();
        load();

        save.addActionListener(e -> save());
        del.addActionListener(e -> delete());
        clear.addActionListener(e -> clearForm());
        search.addActionListener(e -> { page = 1; load(); });
        prev.addActionListener(e -> { if (page > 1) { page--; load(); } });
        next.addActionListener(e -> {
            if (table.getRowCount() == PAGE_SIZE) { page++; load(); }
        });

        table.getSelectionModel().addListSelectionListener(e -> selectRow());
    }

    private void loadCategories() {
        try {
            List<DanhMuc> list = dmBus.findAll();
            cboDm.removeAllItems();
            cboFilterDm.removeAllItems();
            cboFilterDm.addItem("Tat ca");
            for (DanhMuc dm : list) {
                cboDm.addItem(dm);
                cboFilterDm.addItem(dm);
            }
        } catch (Exception e) { MessageUtil.error(this,e); }
    }

    private void load() {
        try {
            BigDecimal min = parseDecimal(txtMinGia.getText());
            BigDecimal max = parseDecimal(txtMaxGia.getText());
            Integer minSl = parseIntNullable(txtMinSl.getText());
            Integer maDm = null;
            Object o = cboFilterDm.getSelectedItem();
            if (o instanceof DanhMuc dm) maDm = dm.getMaDm();

            List<SanPham> list = bus.searchAdvanced(txtSearch.getText(), min, max, minSl, maDm, page, PAGE_SIZE);
            model.setRowCount(0);
            for (SanPham sp : list) {
                model.addRow(new Object[]{
                        sp.getMaSp(), sp.getTenSp(), sp.getDonGia(), sp.getSoLuong(),
                        sp.getTenDm(), sp.getSoLuong() < 5 ? "Sap het hang" : ""
                });
            }
            lblPage.setText("Trang " + page);
        } catch (Exception e) { MessageUtil.error(this,e); }
    }

    private void save() {
        try {
            SanPham sp = new SanPham();
            sp.setMaSp(txtId.getText().isBlank() ? 0 : Integer.parseInt(txtId.getText()));
            sp.setTenSp(txtTen.getText());
            sp.setDonGia(new BigDecimal(txtGia.getText().trim()));
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
            DanhMuc dm = (DanhMuc)cboDm.getSelectedItem();
            sp.setMaDm(dm == null ? null : dm.getMaDm());
            bus.save(sp);
            clearForm(); page = 1; load();
            MessageUtil.info(this,"Luu san pham thanh cong.");
        } catch (NumberFormatException ex) {
            MessageUtil.error(this,new IllegalArgumentException("Gia va so luong phai la so hop le."));
        } catch (Exception e) { MessageUtil.error(this,e); }
    }

    private void delete() {
        try {
            if (txtId.getText().isBlank()) throw new IllegalArgumentException("Vui long chon san pham.");
            if (!MessageUtil.confirm(this,"Xoa san pham dang chon?")) return;
            bus.delete(Integer.parseInt(txtId.getText()));
            clearForm(); load();
        } catch (Exception e) { MessageUtil.error(this,e); }
    }

    private void selectRow() {
        int r = table.getSelectedRow();
        if (r < 0) return;
        txtId.setText(model.getValueAt(r,0).toString());
        txtTen.setText(model.getValueAt(r,1).toString());
        txtGia.setText(model.getValueAt(r,2).toString());
        txtSoLuong.setText(model.getValueAt(r,3).toString());
        String dmName = model.getValueAt(r,4) == null ? "" : model.getValueAt(r,4).toString();
        for (int i=0;i<cboDm.getItemCount();i++) {
            if (cboDm.getItemAt(i).getTenDm().equals(dmName)) {
                cboDm.setSelectedIndex(i); break;
            }
        }
    }

    private void clearForm() {
        txtId.setText(""); txtTen.setText(""); txtGia.setText(""); txtSoLuong.setText("");
        table.clearSelection();
    }

    private BigDecimal parseDecimal(String s) {
        return s == null || s.isBlank() ? null : new BigDecimal(s.trim());
    }

    private Integer parseIntNullable(String s) {
        return s == null || s.isBlank() ? null : Integer.parseInt(s.trim());
    }
}
