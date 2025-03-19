package adapters.persistence.jdbc;

import core.domain.exercise.*;
import core.domain.workout.WorkoutType;
import core.ports.repository.IExerciseRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JdbcExerciseRepository implements IExerciseRepository {
    private final Connection connection;

    public JdbcExerciseRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Exercise> findById(int id) {
        String query = "SELECT * FROM exercises WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToExercise(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Exercise> findAll() {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM exercises";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                exercises.add(mapToExercise(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    @Override
    public void addExercise(Exercise exercise) {
        String query = "INSERT INTO exercises (name, category, difficulty, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, exercise.name());
            stmt.setString(2, exercise.category().name());
            stmt.setString(3, exercise.difficulty().name());
            stmt.setString(4, exercise.description());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<List<Exercise>> findByType(WorkoutType type) {
        List<Exercise> exercises = new ArrayList<>();
        String query = "SELECT * FROM exercises WHERE category = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                exercises.add(mapToExercise(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises.isEmpty() ? Optional.empty() : Optional.of(exercises);
    }

    private Exercise mapToExercise(ResultSet rs) throws SQLException {
        return new Exercise(
                rs.getInt("id"),
                rs.getString("name"),
                ExerciseCategory.valueOf(rs.getString("category")),
                Difficulty.valueOf(rs.getString("difficulty")),
                rs.getString("description"),
                List.of() // TODO: Muskelgruppen in separater Tabelle speichern
        );
    }
}
