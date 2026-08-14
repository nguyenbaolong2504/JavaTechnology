package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.DanhMucBUS;
import vn.edu.eaut.lab5.model.DanhMuc;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DanhMucPanel extends JPanel {
    private final JTextField txtId = new JTextField(6);
    private final JTextField txtTen = new JTextField(24);
    private final DefaultTableModel model = new DefaultTableModel(new Object[]{"Ma DM", "Ten danh muc"}, 0) {
        public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final DanhMucBUS bus = new DanhMucBUS();

    public DanhMucPanel() {
        setLayout(new BorderLayout(8,8));
        txtId.setEditable(false);

        JPanel form = new JPanel();
        form.add(new JLabel("Ma:")); form.add(txtId);
        form.add(new JLabel("Ten danh muc:")); form.add(txtTen);

        JButton save = new JButton("Them / Luu");
        JButton del = new JButton("Xoa");
        JButton refresh = new JButton("Lam moi");
        form.add(save); form.add(del); form.add(refresh);

        save.addActionListener(e -> save());
        del.addActionListener(e -> delete());
        refresh.addActionListener(e -> { clear(); load(); });

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtId.setText(model.getValueAt(r,0).toString());
                txtTen.setText(model.getValueAt(r,1).toString());
            }
        });

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        load();
    }

    private void load() {
        try {
            model.setRowCount(0);
            for (DanhMuc dm : bus.findAll()) model.addRow(new Object[]{dm.getMaDm(), dm.getTenDm()});
        } catch (Exception e) { MessageUtil.error(this,e); }
    }

    private void save() {
        try {
            DanhMuc dm = new DanhMuc();
            dm.setMaDm(txtId.getText().isBlank() ? 0 : Integer.parseInt(txtId.getText()));
            dm.setTenDm(txtTen.getText());
            bus.save(dm);
            clear(); load();
            MessageUtil.info(this, "Luu danh muc thanh cong.");
        } catch (Exception e) { MessageUtil.error(this,e); }
    }

    private void delete() {
        try {
            if (txtId.getText().isBlank()) throw new IllegalArgumentException("Vui long chon danh muc.");
            if (!MessageUtil.confirm(this,"Xoa danh muc dang chon?")) return;
            bus.delete(Integer.parseInt(txtId.getText()));
            clear(); load();
        } catch (Exception e) { MessageUtil.error(this,e); }
    }

    private void clear() { txtId.setText(""); txtTen.setText(""); table.clearSelection(); }
}
