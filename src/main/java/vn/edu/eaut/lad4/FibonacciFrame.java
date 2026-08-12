package vn.edu.eaut.lad4;

import java.awt.GridLayout;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class FibonacciFrame extends JFrame {
    private final JTextField txtN = new JTextField(12);
    private final JButton btnFind = new JButton("Tìm");
    private final JLabel lblResult = new JLabel("Kết quả: ");
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public FibonacciFrame() {
        setTitle("Bài 4 - Fibonacci bằng memoization");
        setSize(560, 230);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        progressBar.setStringPainted(true);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(txtN);
        panel.add(btnFind);
        panel.add(progressBar);
        panel.add(lblResult);
        add(panel);

        btnFind.addActionListener(e -> findFibonacci());
    }

    private BigInteger fibonacci(int n, Map<Integer, BigInteger> memo) {
        if (n <= 1) return BigInteger.valueOf(n);
        if (memo.containsKey(n)) return memo.get(n);
        BigInteger value = fibonacci(n - 1, memo).add(fibonacci(n - 2, memo));
        memo.put(n, value);
        return value;
    }

    private void findFibonacci() {
        int n;
        try {
            n = Integer.parseInt(txtN.getText().trim());
            if (n < 0) {
                JOptionPane.showMessageDialog(this, "N phải >= 0");
                return;
            }
            if (n > 5000) {
                JOptionPane.showMessageDialog(this, "N quá lớn cho cách đệ quy. Vui lòng nhập N <= 5000");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số nguyên hợp lệ");
            return;
        }

        btnFind.setEnabled(false);
        progressBar.setIndeterminate(true);
        lblResult.setText("Đang tính Fibonacci...");

        SwingWorker<BigInteger, Void> worker = new SwingWorker<>() {
            @Override
            protected BigInteger doInBackground() {
                return fibonacci(n, new HashMap<>());
            }

            @Override
            protected void done() {
                try {
                    BigInteger result = get();
                    lblResult.setText("Fibonacci(" + n + ") = " + result);
                } catch (Exception ex) {
                    lblResult.setText("Có lỗi khi tính Fibonacci");
                }
                progressBar.setIndeterminate(false);
                progressBar.setValue(100);
                btnFind.setEnabled(true);
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FibonacciFrame().setVisible(true));
    }
}