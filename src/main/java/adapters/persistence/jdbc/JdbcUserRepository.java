package adapters.persistence.jdbc;

import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import core.ports.repository.IUserRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class JdbcUserRepository implements IUserRepository {
    private final Connection connection;

    public JdbcUserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByUsernameAndPassword(String username, Password password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password.getHashedPassword());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void saveUser(User user) {
        String query = "INSERT INTO users (username, password, weight, height, birthday) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.username());
            stmt.setString(2, user.password().getHashedPassword());
            stmt.setDouble(3, user.gewicht());
            stmt.setDouble(4, user.grosse());
            stmt.setDate(5, Date.valueOf(user.geburtstag().getBirthDate()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {
        String query = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.username());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private User mapToUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getString("username"),
                Password.ofHashed(rs.getString("password")),
                rs.getDouble("weight"),
                rs.getDouble("height"),
                new Birthday(rs.getDate("birthday").toLocalDate())
        );
    }
}
