package vn.edu.eaut.lad4;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class App extends JFrame {
    public App() {
        setTitle("Lab 4 - SwingWorker");
        setSize(520, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(10, 1, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        addButton(panel, "Bài 1 - Đồng hồ đếm ngược", CountdownFrame::new);
        addButton(panel, "Bài 2 - Mô phỏng tải dữ liệu", ProgressDemoFrame::new);
        addButton(panel, "Bài 3 - Tổng số nguyên tố", PrimeSumFrame::new);
        addButton(panel, "Bài 4 - Fibonacci", FibonacciFrame::new);
        addButton(panel, "Bài 5 - Đếm số dòng file", FileLineCounterFrame::new);
        addButton(panel, "Bài 6 - Hủy tác vụ", CancelTaskFrame::new);
        addButton(panel, "Bài 7 - Tìm từ khóa trong file", KeywordSearchFrame::new);
        addButton(panel, "Bài 8 - Thống kê điểm CSV", StudentCsvFrame::new);
        addButton(panel, "Bài 9 - Tải danh sách sản phẩm", ProductLoadFrame::new);
        addButton(panel, "Bài 10 - Quản lý sản phẩm CSV", ProductManagerFrame::new);

        add(new JScrollPane(panel));
    }

    private void addButton(JPanel panel, String text, FrameFactory factory) {
        JButton button = new JButton(text);
        button.addActionListener(e -> factory.create().setVisible(true));
        panel.add(button);
    }

    @FunctionalInterface
    private interface FrameFactory {
        JFrame create();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}