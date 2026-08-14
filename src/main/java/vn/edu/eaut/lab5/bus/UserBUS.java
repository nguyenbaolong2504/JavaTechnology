package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.UserDAL;
import vn.edu.eaut.lab5.model.User;

import java.sql.SQLException;

public class UserBUS {
    private final UserDAL dal = new UserDAL();

    public User login(String username, String password) throws SQLException {
        if (username == null || username.isBlank() || password == null || password.isBlank())
            throw new IllegalArgumentException("Vui long nhap day du tai khoan va mat khau.");
        return dal.authenticate(username.trim(), password);
    }
}
