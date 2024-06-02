package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/horseBet";
    private static final String USER = "postgres";
    private static final String PASSWORD = "133722";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new SQLException("Ошибка при установке соединения с базой данных.", e);
        }
    }
}
