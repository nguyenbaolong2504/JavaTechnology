
package vn.edu.eaut.lab5.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBHelper {
    private static final String URL = System.getenv().getOrDefault(
            "MINISHOP_DB_URL",
            "jdbc:mysql://localhost:3306/minishop_db?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Bangkok"
    );
    private static final String USER = System.getenv().getOrDefault("MINISHOP_DB_USER", "root");
    private static final String PASSWORD = System.getenv().getOrDefault("MINISHOP_DB_PASSWORD", "");

    private DBHelper() {}

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            System.out.println(conn != null ? "Ket noi CSDL thanh cong!" : "Ket noi CSDL that bai!");
        } catch (SQLException e) {
            System.out.println("Ket noi CSDL that bai: " + e.getMessage());
        }
    }
}
