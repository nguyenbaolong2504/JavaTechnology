package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.UserBUS;
import vn.edu.eaut.lab5.model.User;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final JTextField txtUser = new JTextField(18);
    private final JPasswordField txtPass = new JPasswordField(18);
    private final UserBUS bus = new UserBUS();

    public LoginFrame() {
        setTitle("MiniShop - Dang nhap");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(430, 260);
        setLocationRelativeTo(null);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 8, 8, 8);
        g.fill = GridBagConstraints.HORIZONTAL;

        g.gridx = 0; g.gridy = 0; form.add(new JLabel("Tai khoan:"), g);
        g.gridx = 1; form.add(txtUser, g);
        g.gridx = 0; g.gridy = 1; form.add(new JLabel("Mat khau:"), g);
        g.gridx = 1; form.add(txtPass, g);

        JCheckBox show = new JCheckBox("Hien thi mat khau");
        show.addActionListener(e -> txtPass.setEchoChar(show.isSelected() ? (char) 0 : '\u2022'));
        g.gridx = 1; g.gridy = 2; form.add(show, g);

        JButton btn = new JButton("Dang nhap");
        btn.addActionListener(e -> login());
        g.gridy = 3; form.add(btn, g);

        add(form);
        getRootPane().setDefaultButton(btn);
    }

    private void login() {
        try {
            User user = bus.login(txtUser.getText(), new String(txtPass.getPassword()));
            if (user == null) {
                MessageUtil.info(this, "Sai tai khoan hoac mat khau.");
                return;
            }
            dispose();
            new MainFrame(user).setVisible(true);
        } catch (Exception e) {
            MessageUtil.error(this, e);
        }
    }
}
