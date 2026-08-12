package vn.edu.eaut.lad4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class StudentCsvFrame extends JFrame {
    private final DefaultTableModel model = new DefaultTableModel(new String[]{"Mã SV", "Họ tên", "Điểm"}, 0) {
        @Override public boolean isCellEditable(int row, int column) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JButton btnLoad = new JButton("Đọc file CSV");
    private final JLabel lblAvg = new JLabel("Điểm trung bình: ");
    private final JLabel lblMax = new JLabel("Sinh viên điểm cao nhất: ");

    public StudentCsvFrame() {
        setTitle("Bài 8 - Đọc CSV điểm sinh viên");
        setSize(700, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnLoad);

        JPanel bottom = new JPanel(new GridLayout(2, 1));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 10, 10, 10));
        bottom.add(lblAvg);
        bottom.add(lblMax);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        btnLoad.addActionListener(e -> chooseAndLoad());
    }

    private void chooseAndLoad() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("CSV files (*.csv)", "csv"));
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;

        File file = chooser.getSelectedFile();
        btnLoad.setEnabled(false);
        model.setRowCount(0);
        lblAvg.setText("Đang đọc dữ liệu...");
        lblMax.setText("");

        SwingWorker<List<Student>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Student> doInBackground() throws Exception {
                List<Student> students = new ArrayList<>();
                try (BufferedReader reader = Files.newBufferedReader(file.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    boolean first = true;
                    while ((line = reader.readLine()) != null) {
                        if (first) { first = false; continue; }
                        if (line.isBlank()) continue;
                        String[] parts = line.split(",", 3);
                        if (parts.length != 3) continue;
                        try {
                            students.add(new Student(parts[0].trim(), parts[1].trim(), Double.parseDouble(parts[2].trim())));
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
                return students;
            }

            @Override
            protected void done() {
                try {
                    List<Student> students = get();
                    double sum = 0;
                    Student best = null;
                    for (Student s : students) {
                        model.addRow(new Object[]{s.id(), s.name(), s.score()});
                        sum += s.score();
                        if (best == null || s.score() > best.score()) best = s;
                    }
                    lblAvg.setText(students.isEmpty() ? "Điểm trung bình: Không có dữ liệu" : String.format("Điểm trung bình: %.2f", sum / students.size()));
                    lblMax.setText(best == null ? "Sinh viên điểm cao nhất: Không có dữ liệu" : "Sinh viên điểm cao nhất: " + best.id() + " - " + best.name() + " (" + best.score() + ")");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(StudentCsvFrame.this, "Lỗi đọc file CSV: " + ex.getMessage());
                }
                btnLoad.setEnabled(true);
            }
        };
        worker.execute();
    }

    private record Student(String id, String name, double score) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentCsvFrame().setVisible(true));
    }
}