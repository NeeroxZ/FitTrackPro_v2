package de.neeroxz.db;

/**
 * Class: DatabaseConnectionFactory
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionFactory {
    private static final String URL = "jdbc:h2:./fittracker";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Fehler bei der Datenbankverbindung", e);
        }
    }
}
