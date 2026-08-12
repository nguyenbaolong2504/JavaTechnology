package vn.edu.eaut.lad4;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FileLineCounterFrame extends JFrame {
    private File selectedFile;
    private final JLabel lblFile = new JLabel("Chưa chọn file");
    private final JLabel lblResult = new JLabel("Số dòng: ");
    private final JButton btnChoose = new JButton("Chọn file");
    private final JButton btnCount = new JButton("Đếm dòng");
    private final JProgressBar progressBar = new JProgressBar(0, 100);

    public FileLineCounterFrame() {
        setTitle("Bài 5 - Đếm số dòng file");
        setSize(650, 260);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        progressBar.setStringPainted(true);

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(btnChoose);
        buttons.add(btnCount);

        JPanel panel = new JPanel(new GridLayout(4, 1, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(buttons);
        panel.add(lblFile);
        panel.add(progressBar);
        panel.add(lblResult);
        add(panel);

        btnChoose.addActionListener(e -> chooseFile());
        btnCount.addActionListener(e -> countLines());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void countLines() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file trước");
            return;
        }

        btnCount.setEnabled(false);
        progressBar.setValue(0);
        lblResult.setText("Đang đọc file...");

        SwingWorker<Long, Void> worker = new SwingWorker<>() {
            @Override
            protected Long doInBackground() throws Exception {
                long totalBytes = Files.size(selectedFile.toPath());
                long readBytes = 0;
                long lines = 0;
                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines++;
                        readBytes += line.getBytes(StandardCharsets.UTF_8).length + System.lineSeparator().getBytes(StandardCharsets.UTF_8).length;
                        int progress = totalBytes == 0 ? 100 : (int) Math.min(100, readBytes * 100 / totalBytes);
                        setProgress(progress);
                    }
                }
                return lines;
            }

            @Override
            protected void done() {
                try {
                    lblResult.setText("Số dòng: " + get());
                } catch (Exception ex) {
                    lblResult.setText("Lỗi khi đọc file");
                }
                progressBar.setValue(100);
                btnCount.setEnabled(true);
            }
        };

        worker.addPropertyChangeListener(evt -> {
            if ("progress".equals(evt.getPropertyName())) {
                progressBar.setValue((int) evt.getNewValue());
            }
        });
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FileLineCounterFrame().setVisible(true));
    }
}
