package adapters.persistence.jdbc;

import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.domain.exercise.TrainingSplit;
import core.ports.repository.IWorkoutRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcWorkoutRepository implements IWorkoutRepository {
    private final Connection connection;

    public JdbcWorkoutRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void saveWorkout(Workout workout) {
        String query = "INSERT INTO workouts (name, type, username, frequency, split) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, workout.name());
            stmt.setString(2, workout.type().name());
            stmt.setString(3, workout.username());
            stmt.setInt(4, workout.frequency());
            stmt.setString(5, workout.split().name());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedId = generatedKeys.getInt(1);
                System.out.println("✅ Workout gespeichert mit ID: " + generatedId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Workout> findAll() {
        List<Workout> workouts = new ArrayList<>();
        String query = "SELECT * FROM workouts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                workouts.add(mapToWorkout(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workouts;
    }

    @Override
    public Optional<Workout> findById(int id) {
        String query = "SELECT * FROM workouts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapToWorkout(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                workouts.add(mapToWorkout(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return workouts;
    }

    @Override
    public void deleteById(int id) {
        String query = "DELETE FROM workouts WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Workout gelöscht mit ID: " + id);
            } else {
                System.out.println("❌ Kein Workout mit ID " + id + " gefunden.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Workout mapToWorkout(ResultSet rs) throws SQLException {
        return new Workout(
                rs.getInt("id"),
                rs.getString("name"),
                WorkoutType.valueOf(rs.getString("type")),
                List.of(), // TODO: TrainingDays aus separater Tabelle holen
                rs.getString("username"),
                rs.getInt("frequency"),
                TrainingSplit.valueOf(rs.getString("split"))
        );
    }
}
