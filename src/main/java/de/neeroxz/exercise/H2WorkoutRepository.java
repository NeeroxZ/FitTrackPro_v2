package de.neeroxz.exercise;

/**
 * Class: H2WorkoutRepository
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class H2WorkoutRepository implements WorkoutRepository {
    private final Connection connection;

    public H2WorkoutRepository(Connection connection) {
        this.connection = connection;
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS workouts (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "type VARCHAR(50), " +
                "exercise_ids VARCHAR(255), " +
                "username VARCHAR(255))"; // 🔥 Neuer Spaltenwert
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Erstellen der Workout-Tabelle", e);
        }
    }

    @Override
    public void saveWorkout(Workout workout) {
        String query = "INSERT INTO workouts (name, type, exercise_ids, username) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, workout.name());
            stmt.setString(2, workout.type().name());

            String exerciseIds = workout.exercises().stream()
                    .map(e -> String.valueOf(e.id()))
                    .reduce((a, b) -> a + "," + b)
                    .orElse("");

            stmt.setString(3, exerciseIds);
            stmt.setString(4, workout.username());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Speichern des Workouts", e);
        }
    }

    @Override
    public List<Workout> findAll() {
        return List.of();
    }

    @Override
    public Optional<Workout> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Workout> findByUser(String username) {
        List<Workout> workouts = new ArrayList<>();
        String query = "SELECT * FROM workouts WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                workouts.add(new Workout(
                        rs.getInt("id"),
                        rs.getString("name"),
                        WorkoutType.valueOf(rs.getString("type")),
                        new ArrayList<>(),
                        rs.getString("username")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Laden der Workouts", e);
        }
        return workouts;
    }
}
