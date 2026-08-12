package vn.edu.eaut.lad4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class ProductManagerFrame extends JFrame {
    private final JTextField txtCode = new JTextField(10);
    private final JTextField txtName = new JTextField(15);
    private final JTextField txtPrice = new JTextField(10);
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "Đơn giá"}, 0);
    private final JTable table = new JTable(model);
    private final JButton btnAdd = new JButton("Thêm");
    private final JButton btnUpdate = new JButton("Sửa");
    private final JButton btnDelete = new JButton("Xóa");
    private final JButton btnLoad = new JButton("Đọc file CSV");
    private final JButton btnSave = new JButton("Lưu file CSV");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel lblStatus = new JLabel("Sẵn sàng");

    public ProductManagerFrame() {
        setTitle("Bài 10 - Mini project quản lý sản phẩm CSV");
        setSize(850, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        progressBar.setStringPainted(true);

        JPanel input = new JPanel(new FlowLayout(FlowLayout.LEFT));
        input.add(new JLabel("Mã SP:")); input.add(txtCode);
        input.add(new JLabel("Tên SP:")); input.add(txtName);
        input.add(new JLabel("Đơn giá:")); input.add(txtPrice);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actions.add(btnAdd); actions.add(btnUpdate); actions.add(btnDelete); actions.add(btnLoad); actions.add(btnSave);

        JPanel top = new JPanel(new GridLayout(4, 1, 4, 4));
        top.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        top.add(input);
        top.add(actions);
        top.add(progressBar);
        top.add(lblStatus);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());
        btnLoad.addActionListener(e -> loadCsv());
        btnSave.addActionListener(e -> saveCsv());
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelectedRow());
    }

    private Product readForm() {
        String code = txtCode.getText().trim();
        String name = txtName.getText().trim();
        String priceText = txtPrice.getText().trim();
        if (code.isEmpty() || name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mã, tên và đơn giá");
            return null;
        }
        try {
            long price = Long.parseLong(priceText);
            if (price < 0) throw new NumberFormatException();
            return new Product(code, name, price);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Đơn giá phải là số nguyên không âm");
            return null;
        }
    }

    private void addProduct() {
        Product p = readForm();
        if (p == null) return;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (p.code().equalsIgnoreCase(String.valueOf(model.getValueAt(i, 0)))) {
                JOptionPane.showMessageDialog(this, "Mã sản phẩm đã tồn tại");
                return;
            }
        }
        model.addRow(new Object[]{p.code(), p.name(), p.price()});
        clearForm();
        lblStatus.setText("Đã thêm sản phẩm");
    }

    private void updateProduct() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa");
            return;
        }
        Product p = readForm();
        if (p == null) return;
        model.setValueAt(p.code(), row, 0);
        model.setValueAt(p.name(), row, 1);
        model.setValueAt(p.price(), row, 2);
        lblStatus.setText("Đã cập nhật sản phẩm");
    }

    private void deleteProduct() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa");
            return;
        }
        model.removeRow(row);
        clearForm();
        lblStatus.setText("Đã xóa sản phẩm");
    }

    private void fillFormFromSelectedRow() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtCode.setText(String.valueOf(model.getValueAt(row, 0)));
            txtName.setText(String.valueOf(model.getValueAt(row, 1)));
            txtPrice.setText(String.valueOf(model.getValueAt(row, 2)));
        }
    }

    private void clearForm() {
        txtCode.setText(""); txtName.setText(""); txtPrice.setText("");
        table.clearSelection();
    }

    private void setBusy(boolean busy) {
        btnAdd.setEnabled(!busy); btnUpdate.setEnabled(!busy); btnDelete.setEnabled(!busy);
        btnLoad.setEnabled(!busy); btnSave.setEnabled(!busy);
    }

    private void loadCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();

        setBusy(true);
        progressBar.setValue(0);
        lblStatus.setText("Đang đọc file CSV...");

        SwingWorker<List<Product>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Product> doInBackground() throws Exception {
                List<Product> list = new ArrayList<>();
                long total = Files.size(file.toPath());
                long read = 0;
                try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    boolean first = true;
                    while ((line = reader.readLine()) != null) {
                        read += line.getBytes(StandardCharsets.UTF_8).length + 1;
                        if (first && line.toLowerCase().contains("ma")) { first = false; continue; }
                        first = false;
                        if (line.isBlank()) continue;
                        String[] parts = line.split(",", 3);
                        if (parts.length == 3) {
                            try { list.add(new Product(parts[0].trim(), parts[1].trim(), Long.parseLong(parts[2].trim()))); }
                            catch (NumberFormatException ignored) {}
                        }
                        setProgress(total == 0 ? 100 : (int) Math.min(100, read * 100 / total));
                    }
                }
                return list;
            }

            @Override
            protected void done() {
                try {
                    model.setRowCount(0);
                    for (Product p : get()) model.addRow(new Object[]{p.code(), p.name(), p.price()});
                    progressBar.setValue(100);
                    lblStatus.setText("Đọc file CSV hoàn tất");
                } catch (Exception ex) {
                    lblStatus.setText("Lỗi khi đọc file CSV");
                }
                setBusy(false);
            }
        };
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) progressBar.setValue((int) evt.getNewValue());
        });
        worker.execute();
    }

    private void saveCsv() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        chooser.setSelectedFile(new File("products.csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) return;
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(".csv")) file = new File(file.getAbsolutePath() + ".csv");
        final File targetFile = file;

        setBusy(true);
        progressBar.setValue(0);
        lblStatus.setText("Đang lưu file CSV...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                try (BufferedWriter writer = Files.newBufferedWriter(targetFile.toPath(), StandardCharsets.UTF_8)) {
                    writer.write("MaSP,TenSP,DonGia");
                    writer.newLine();
                    int count = model.getRowCount();
                    for (int i = 0; i < count; i++) {
                        writer.write(model.getValueAt(i, 0) + "," + model.getValueAt(i, 1) + "," + model.getValueAt(i, 2));
                        writer.newLine();
                        setProgress(count == 0 ? 100 : (i + 1) * 100 / count);
                    }
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    progressBar.setValue(100);
                    lblStatus.setText("Đã lưu: " + targetFile.getAbsolutePath());
                } catch (Exception ex) {
                    lblStatus.setText("Lỗi khi lưu file CSV");
                }
                setBusy(false);
            }
        };
        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) progressBar.setValue((int) evt.getNewValue());
        });
        worker.execute();
    }

    private record Product(String code, String name, long price) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductManagerFrame().setVisible(true));
    }
}
