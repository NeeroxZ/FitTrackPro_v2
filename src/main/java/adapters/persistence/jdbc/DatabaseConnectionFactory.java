package adapters.persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    private static final String JDBC_URL = "jdbc:h2:./fittrackerdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static Connection connection;

    private DatabaseConnectionFactory() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                System.out.println("✅ Verbindung zur H2-Datenbank hergestellt!");
            } catch (SQLException e) {
                throw new RuntimeException("❌ Fehler beim Herstellen der DB-Verbindung", e);
            }
        }
        return connection;
    }
}
