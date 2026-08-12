package vn.edu.eaut.lad4;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

public class KeywordSearchFrame extends JFrame {
    private File selectedFile;
    private final JLabel lblFile = new JLabel("Chưa chọn file");
    private final JTextField txtKeyword = new JTextField(20);
    private final JButton btnChoose = new JButton("Chọn file .txt");
    private final JButton btnSearch = new JButton("Tìm kiếm");
    private final JTextArea txtResult = new JTextArea();
    private final JLabel lblCount = new JLabel("Số dòng tìm thấy: 0");

    public KeywordSearchFrame() {
        setTitle("Bài 7 - Tìm từ khóa trong file lớn");
        setSize(760, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        txtResult.setEditable(false);
        txtResult.setLineWrap(true);
        txtResult.setWrapStyleWord(true);

        JPanel top = new JPanel(new GridLayout(3, 1, 5, 5));
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        top.add(lblFile);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.add(btnChoose);
        controls.add(new JLabel("Từ khóa:"));
        controls.add(txtKeyword);
        controls.add(btnSearch);
        top.add(controls);
        top.add(lblCount);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(txtResult), BorderLayout.CENTER);

        btnChoose.addActionListener(e -> chooseFile());
        btnSearch.addActionListener(e -> searchKeyword());
    }

    private void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Text files (*.txt)", "txt"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();
            lblFile.setText("File: " + selectedFile.getAbsolutePath());
        }
    }

    private void searchKeyword() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn file .txt trước");
            return;
        }
        String keyword = txtKeyword.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa");
            return;
        }

        txtResult.setText("");
        lblCount.setText("Đang tìm kiếm...");
        btnSearch.setEnabled(false);

        SwingWorker<Integer, String> worker = new SwingWorker<>() {
            @Override
            protected Integer doInBackground() throws Exception {
                int count = 0;
                int lineNumber = 0;
                String lowerKeyword = keyword.toLowerCase();
                try (BufferedReader reader = Files.newBufferedReader(selectedFile.toPath(), StandardCharsets.UTF_8)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.toLowerCase().contains(lowerKeyword)) {
                            count++;
                            publish("Dòng " + lineNumber + ": " + line);
                        }
                    }
                }
                return count;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String line : chunks) txtResult.append(line + System.lineSeparator());
            }

            @Override
            protected void done() {
                try {
                    lblCount.setText("Số dòng tìm thấy: " + get());
                } catch (Exception ex) {
                    lblCount.setText("Có lỗi khi tìm kiếm");
                }
                btnSearch.setEnabled(true);
            }
        };
        worker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KeywordSearchFrame().setVisible(true));
    }
}
