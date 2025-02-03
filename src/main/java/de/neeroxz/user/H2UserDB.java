package de.neeroxz.user;

/**
 * Class: DB2UserAdapter
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
import java.sql.*;
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

    @Override
    public Optional<User> findUserByUsername(String username) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "SELECT * FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new User(
                        rs.getString("username"),
                        new Password(rs.getString("password")),
                        rs.getDouble("gewicht"),
                        rs.getDouble("grosse"),
                        new Birthday(rs.getDate("geburtstag").toLocalDate())
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Abrufen des Benutzers", e);
        }
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {
        try (PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO users (username, password, gewicht, grosse, geburtstag) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, user.username());
            stmt.setString(2, user.password().getHashedPassword());
            stmt.setDouble(3, user.gewicht());
            stmt.setDouble(4, user.grosse());
            stmt.setDate(5, Date.valueOf(user.geburtstag().getBirthDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern des Benutzers", e);
        }
    }
}
