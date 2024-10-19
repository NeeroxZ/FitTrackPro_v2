package de.neeroxz.user;

/**
 * Class: DB2UserAdapter
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class H2UserDB implements UserRepository {
    private Connection connection;

    public H2UserDB(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findUserByUsernameAndPassword(String username, String hashedPassword) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Benutzername und gehashtes Passwort setzen
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Wenn ein Benutzer gefunden wird, wird er hier zurückgegeben
                User user = new User(
                        rs.getString("username"),
                        Password.ofHashed(rs.getString("password")), // Gehashtes Passwort aus der DB
                        rs.getDouble("gewicht"),
                        rs.getDouble("grosse"),
                        new Birthday(rs.getDate("geburtstag").toLocalDate())
                );
                return Optional.of(user);
            } else {
                // Kein Benutzer gefunden
                return Optional.empty();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
