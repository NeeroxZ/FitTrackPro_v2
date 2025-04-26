package adapters.persistence.jdbc;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DatabaseInitializer {
    public static void initializeDatabase() {
        try (Connection connection = DatabaseConnectionFactory.getConnection()) {
            executeSqlFile(connection, "/sql/schema.sql");
            executeSqlFile(connection, "/sql/data.sql");
        } catch (Exception e) {
            throw new RuntimeException("❌ Fehler bei der Datenbank-Initialisierung", e);
        }
    }

    private static void executeSqlFile(Connection connection, String filePath) {
        try (InputStream is = DatabaseInitializer.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             Statement stmt = connection.createStatement()) {

            String sql = reader.lines()
                               .map(String::trim)
                               .filter(line -> !line.isEmpty() && !line.startsWith("--"))
                               .collect(Collectors.joining(" "));

            for (String statement : sql.split(";")) {
                if (!statement.trim().isEmpty()) {
                    stmt.execute(statement.trim());
                }
            }

            System.out.println("✅ SQL-Datei geladen: " + filePath);
        } catch (Exception e) {
            throw new RuntimeException("❌ Fehler beim Laden der SQL-Datei: " + filePath, e);
        }
    }
}
