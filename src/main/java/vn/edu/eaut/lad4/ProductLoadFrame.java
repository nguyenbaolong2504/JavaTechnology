package vn.edu.eaut.lad4;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class ProductLoadFrame extends JFrame {
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "Đơn giá"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JButton btnLoad = new JButton("Tải sản phẩm");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel lblStatus = new JLabel("Chưa tải dữ liệu");

    public ProductLoadFrame() {
        setTitle("Bài 9 - Mô phỏng tải danh sách sản phẩm");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        progressBar.setStringPainted(true);

        JPanel top = new JPanel(new GridLayout(3, 1, 5, 5));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        top.add(btnLoad);
        top.add(progressBar);
        top.add(lblStatus);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnLoad.addActionListener(e -> loadProducts());
    }

    private void loadProducts() {
        btnLoad.setEnabled(false);
        model.setRowCount(0);
        progressBar.setValue(0);
        lblStatus.setText("Đang tải sản phẩm...");

        List<Product> products = List.of(
            new Product("SP01", "Bàn phím", 250000),
            new Product("SP02", "Chuột", 150000),
            new Product("SP03", "Màn hình", 2500000),
            new Product("SP04", "Tai nghe", 450000),
            new Product("SP05", "Webcam", 600000)
        );

        SwingWorker<Void, Product> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < products.size(); i++) {
                    Thread.sleep(700);
                    publish(products.get(i));
                    setProgress((i + 1) * 100 / products.size());
                }
                return null;
            }

            @Override
            protected void process(List<Product> chunks) {
                for (Product p : chunks) model.addRow(new Object[]{p.code(), p.name(), p.price()});
            }

            @Override
            protected void done() {
                progressBar.setValue(100);
                lblStatus.setText("Tải dữ liệu hoàn tất");
                btnLoad.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) progressBar.setValue((int) evt.getNewValue());
        });
        worker.execute();
    }

    private record Product(String code, String name, long price) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ProductLoadFrame().setVisible(true));
    }
}
