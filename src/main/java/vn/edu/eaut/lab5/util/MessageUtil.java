package vn.edu.eaut.lab5.util;

import javax.swing.*;
import java.awt.*;

public final class MessageUtil {
    private MessageUtil() {}

    public static void info(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Thong bao", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component parent, Throwable e) {
        JOptionPane.showMessageDialog(parent, e.getMessage(), "Loi", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean confirm(Component parent, String msg) {
        return JOptionPane.showConfirmDialog(parent, msg, "Xac nhan", JOptionPane.YES_NO_OPTION)
                == JOptionPane.YES_OPTION;
    }
}
