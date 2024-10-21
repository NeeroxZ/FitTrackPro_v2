package de.neeroxz.exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * Class: H2ExerciseRepository
 *
 * @author NeeroxZ
 * @date 21.10.2024
 *
 * The H2ExerciseRepository handles the database operations related to the Exercise entity
 * using an H2 database. It implements the ExerciseRepository interface.
 */
public class H2ExerciseRepository implements ExerciseRepository {

    private final Connection connection;

    /**
     * Constructor to initialize the repository with a database connection.
     *
     * @param connection The connection object for the H2 database.
     */
    public H2ExerciseRepository(Connection connection) {
        this.connection = connection;
    }

    /**
     * Finds an exercise by its ID from the database.
     *
     * @param id The ID of the exercise.
     * @return An Optional containing the Exercise if found, or an empty Optional if not found.
     */
    @Override
    public Optional<Exercise> findById(int id) {
        String query = "SELECT * FROM exercises WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Convert the category from a String to an ExerciseCategory enum
                    ExerciseCategory category = ExerciseCategory.valueOf(rs.getString("category").toUpperCase());

                    // Create a new Exercise record
                    Exercise exercise = new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            category,
                            rs.getString("difficulty")
                    );
                    return Optional.of(exercise);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Retrieves all exercises stored in the database.
     *
     * @return A list of all exercises in the database.
     */
    @Override
    public List<Exercise> findAll() {
        String query = "SELECT * FROM exercises";
        List<Exercise> exercises = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Convert the category from a String to an ExerciseCategory enum
                ExerciseCategory category = ExerciseCategory.valueOf(rs.getString("category").toUpperCase());

                // Create a new Exercise record
                Exercise exercise = new Exercise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        category,
                        rs.getString("difficulty")
                );
                exercises.add(exercise);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exercises;
    }
}
