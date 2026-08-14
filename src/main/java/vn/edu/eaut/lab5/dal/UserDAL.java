package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.User;

import java.sql.*;

public class UserDAL {
    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT username, ho_ten, vai_tro FROM tai_khoan WHERE username=? AND password=?";
        try (Connection c = DBHelper.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getString("username"), rs.getString("ho_ten"), rs.getString("vai_tro"));
                }
            }
        }
        return null;
    }
}
