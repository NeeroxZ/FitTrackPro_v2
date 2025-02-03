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
                int workoutId = rs.getInt("id");
                String name = rs.getString("name");
                WorkoutType type = WorkoutType.valueOf(rs.getString("type"));
                String exerciseIds = rs.getString("exercise_ids"); // 🔥 Übungs-IDs als String
                List<Exercise> exercises = loadExercisesByIds(exerciseIds); // ✅ Übungen laden

                workouts.add(new Workout(workoutId, name, type, exercises, username));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Laden der Workouts", e);
        }
        return workouts;
    }

    private List<Exercise> loadExercisesByIds(String exerciseIds) {
        List<Exercise> exercises = new ArrayList<>();

        if (exerciseIds == null || exerciseIds.isEmpty()) {
            return exercises; // 🔥 Falls keine Übungen vorhanden sind
        }

        String query = "SELECT * FROM exercises WHERE id IN (" + exerciseIds + ")";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ExerciseCategory category = ExerciseCategory.valueOf(rs.getString("category").toUpperCase());

                Exercise exercise = new Exercise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        category,
                        rs.getString("difficulty")
                );
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Fehler beim Laden der Übungen für Workout", e);
        }
        return exercises;
    }

}
