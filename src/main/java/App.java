
import de.neeroxz.exercise.*;
import de.neeroxz.services.AuthenticationService;
import de.neeroxz.ui.AppPanel;
import de.neeroxz.user.*;

import java.sql.Connection;

/**
 * Class: App
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class App {
    public static void main(String[] args) {
    //      H2InitDatabase db = new H2InitDatabase();
    //        db.initDatabase();

       /*
        Connection connection = DatabaseConnectionFactory.getConnection();

        UserRepository userRepository = new H2UserDB(connection);
         ExerciseRepository exerciseRepository = new H2ExerciseRepository(connection);
        WorkoutRepository  workoutRepository = new H2WorkoutRepository(connection);
        */
        PasswordHasher passwordHasher = new SHA256PasswordHasher();

        ExerciseRepository exerciseRepository = new InMemoryExerciseRepository();
        WorkoutRepository workoutRepository = new InMemoryWorkoutRepository();
        UserRepository userRepository = new InMemoryUserRepository();



        WorkoutService workoutService = new WorkoutService(workoutRepository, exerciseRepository);
        AuthenticationService authService = new AuthenticationService(userRepository, passwordHasher);

        new AppPanel(authService,workoutService).showPanel();
    }
}
