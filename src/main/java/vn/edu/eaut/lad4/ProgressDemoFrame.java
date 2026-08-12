package vn.edu.eaut.lad4;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
public class ProgressDemoFrame extends JFrame {
private JButton btnLoad;
private JProgressBar progressBar;
private JLabel lblStatus;
public ProgressDemoFrame() {
setTitle("Bài 2 - Mô phỏng tải dữ liệu");
setSize(450, 180);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
setLocationRelativeTo(null);
btnLoad = new JButton("Tải dữ liệu");
progressBar = new JProgressBar(0, 100);
progressBar.setStringPainted(true);
lblStatus = new JLabel("Chưa tải dữ liệu");
JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
panel.add(btnLoad);
panel.add(progressBar);
panel.add(lblStatus);
add(panel);
btnLoad.addActionListener(e -> loadData());
}
private void loadData() {
btnLoad.setEnabled(false);
progressBar.setValue(0);
lblStatus.setText("Đang tải dữ liệu...");
SwingWorker<Void, Integer> worker = new SwingWorker<>() {
@Override
protected Void doInBackground() throws Exception {
for (int i = 0; i <= 100; i += 10) {
setProgress(i);
Thread.sleep(1000);
}
return null;
}
@Override
protected void done() {
progressBar.setValue(100);
lblStatus.setText("Tải dữ liệu hoàn tất");
btnLoad.setEnabled(true);
}
};
worker.addPropertyChangeListener(evt -> {
if ("progress".equals(evt.getPropertyName())) {
progressBar.setValue((int) evt.getNewValue());
}
});
worker.execute();
}
}