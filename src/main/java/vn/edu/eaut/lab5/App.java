package vn.edu.eaut.lab5;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.ui.LoginFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        DBHelper.testConnection();
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {
            }
            new LoginFrame().setVisible(true);
        });
    }
}
