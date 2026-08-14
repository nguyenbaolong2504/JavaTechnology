package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.util.MessageUtil;
import vn.edu.eaut.lab5.util.PhoneDocumentFilter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class KhachHangPanel extends JPanel {
    private final JTextField txtId = new JTextField(5);
    private final JTextField txtTen = new JTextField(18);
    private final JTextField txtSdt = new JTextField(10);
    private final JTextField txtDiaChi = new JTextField(18);
    private final JTextField txtSearch = new JTextField(20);
    private final KhachHangBUS bus = new KhachHangBUS();

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Ma KH","Ten KH","SDT","Dia chi"},0) {
        public boolean isCellEditable(int r,int c){return false;}
    };
    private final JTable table = new JTable(model);

    public KhachHangPanel() {
        setLayout(new BorderLayout(6,6));
        txtId.setEditable(false);
        ((AbstractDocument)txtSdt.getDocument()).setDocumentFilter(new PhoneDocumentFilter());

        JPanel form = new JPanel();
        form.add(new JLabel("Ma:")); form.add(txtId);
        form.add(new JLabel("Ten:")); form.add(txtTen);
        form.add(new JLabel("SDT:")); form.add(txtSdt);
        form.add(new JLabel("Dia chi:")); form.add(txtDiaChi);

        JButton save = new JButton("Them / Luu");
        JButton del = new JButton("Xoa");
        JButton clear = new JButton("Lam moi");
        form.add(save); form.add(del); form.add(clear);

        JPanel search = new JPanel();
        search.add(new JLabel("Tim ten / SDT / dia chi:")); search.add(txtSearch);
        JButton btnSearch = new JButton("Tim");
        search.add(btnSearch);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form,BorderLayout.NORTH); top.add(search,BorderLayout.SOUTH);

        add(top,BorderLayout.NORTH);
        add(new JScrollPane(table),BorderLayout.CENTER);

        save.addActionListener(e->save());
        del.addActionListener(e->delete());
        clear.addActionListener(e->{clear();load();});
        btnSearch.addActionListener(e->load());
        table.getSelectionModel().addListSelectionListener(e->selectRow());
        load();
    }

    private void load() {
        try {
            model.setRowCount(0);
            for (KhachHang kh : bus.search(txtSearch.getText()))
                model.addRow(new Object[]{kh.getMaKh(),kh.getTenKh(),kh.getSdt(),kh.getDiaChi()});
        } catch (Exception e){ MessageUtil.error(this,e); }
    }

    private void save() {
        try {
            KhachHang kh = new KhachHang();
            kh.setMaKh(txtId.getText().isBlank()?0:Integer.parseInt(txtId.getText()));
            kh.setTenKh(txtTen.getText());
            kh.setSdt(txtSdt.getText());
            kh.setDiaChi(txtDiaChi.getText());
            bus.save(kh);
            clear();load();
            MessageUtil.info(this,"Luu khach hang thanh cong.");
        } catch(Exception e){MessageUtil.error(this,e);}
    }

    private void delete() {
        try {
            if(txtId.getText().isBlank()) throw new IllegalArgumentException("Vui long chon khach hang.");
            if(!MessageUtil.confirm(this,"Xoa khach hang dang chon?")) return;
            bus.delete(Integer.parseInt(txtId.getText()));
            clear();load();
        } catch(Exception e){MessageUtil.error(this,e);}
    }

    private void selectRow() {
        int r=table.getSelectedRow();
        if(r<0)return;
        txtId.setText(model.getValueAt(r,0).toString());
        txtTen.setText(model.getValueAt(r,1).toString());
        txtSdt.setText(model.getValueAt(r,2).toString());
        Object a=model.getValueAt(r,3);
        txtDiaChi.setText(a==null?"":a.toString());
    }

    private void clear() {
        txtId.setText("");txtTen.setText("");txtSdt.setText("");txtDiaChi.setText("");
        table.clearSelection();
    }
}
