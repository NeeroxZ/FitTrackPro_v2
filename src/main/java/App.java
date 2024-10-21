import de.neeroxz.db.H2InitDatabase;
import de.neeroxz.exercise.Exercise;
import de.neeroxz.exercise.ExerciseService;
import de.neeroxz.exercise.H2ExerciseRepository;
import de.neeroxz.ui.AppPanel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

/**
 * Class: App
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class App {
    public static void main(String[] args) {
       /*
        System.out.println("test");
        H2InitDatabase db = new H2InitDatabase();
        db.initDatabase();
        new AppPanel().showPanel();
    */
        String url = "jdbc:h2:./fittracker"; // H2-Datenbankverbindung
        String username = "sa";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            H2ExerciseRepository exerciseRepository = new H2ExerciseRepository(connection);
            ExerciseService exerciseService = new ExerciseService(exerciseRepository);

            // Abrufen aller Übungen
            List<Exercise> allExercises = exerciseService.getAllExercises();
            allExercises.forEach(System.out::println);

            // Abrufen einer Übung nach ID
            Optional<Exercise> exercise = exerciseService.getExerciseById(1);
            exercise.ifPresent(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
