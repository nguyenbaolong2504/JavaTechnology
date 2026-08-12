package vn.edu.eaut.lad4;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class CancelTaskFrame extends JFrame {
    private final JButton btnStart = new JButton("Bắt đầu tải");
    private final JButton btnCancel = new JButton("Hủy");
    private final JProgressBar progressBar = new JProgressBar(0, 100);
    private final JLabel lblStatus = new JLabel("Chưa chạy tác vụ");
    private SwingWorker<Void, Void> worker;

    public CancelTaskFrame() {
        setTitle("Bài 6 - Hủy tác vụ SwingWorker");
        setSize(500, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        progressBar.setStringPainted(true);
        btnCancel.setEnabled(false);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(btnStart);
        buttons.add(btnCancel);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(buttons);
        panel.add(progressBar);
        panel.add(lblStatus);
        add(panel);

        btnStart.addActionListener(e -> startTask());
        btnCancel.addActionListener(e -> cancelTask());
    }

    private void startTask() {
        btnStart.setEnabled(false);
        btnCancel.setEnabled(true);
        progressBar.setValue(0);
        lblStatus.setText("Đang thực hiện tác vụ...");

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i <= 100; i++) {
                    if (isCancelled()) break;
                    setProgress(i);
                    Thread.sleep(100);
                }
                return null;
            }

            @Override
            protected void done() {
                if (isCancelled()) {
                    lblStatus.setText("Đã hủy tác vụ");
                } else {
                    progressBar.setValue(100);
                    lblStatus.setText("Tác vụ hoàn tất");
                }
                btnStart.setEnabled(true);
                btnCancel.setEnabled(false);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });
        worker.execute();
    }

    private void cancelTask() {
        if (worker != null && !worker.isDone()) {
            worker.cancel(true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CancelTaskFrame().setVisible(true));
    }
}