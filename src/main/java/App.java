
import de.neeroxz.exercise.repository.IExerciseRepository;
import de.neeroxz.exercise.repository.InMemoryExerciseRepository;
import de.neeroxz.exercise.repository.InMemoryWorkoutRepository;
import de.neeroxz.exercise.repository.WorkoutRepository;
import de.neeroxz.exercise.service.ExerciseService;
import de.neeroxz.exercise.service.WorkoutService;
import de.neeroxz.input.ConsoleInputReader;
import de.neeroxz.input.InputReader;
import de.neeroxz.security.PasswordHasher;
import de.neeroxz.services.AuthenticationService;
import de.neeroxz.user.service.UserService;
import de.neeroxz.ui.console.AppPanel;
import de.neeroxz.user.repository.InMemoryUserRepository;
import de.neeroxz.user.service.UserRepository;
import de.neeroxz.util.SHA256PasswordHasher;

import java.util.Scanner;

/**
 * Class: App
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class App
{
    public static void main(String[] args)
    {

        PasswordHasher passwordHasher = new SHA256PasswordHasher();

        IExerciseRepository exerciseRepository = new InMemoryExerciseRepository();
        WorkoutRepository workoutRepository = new InMemoryWorkoutRepository();
        UserRepository userRepository = new InMemoryUserRepository();

        InputReader inputReader = new ConsoleInputReader(new Scanner(System.in));

        UserService userService = new UserService(userRepository);
        ExerciseService exerciseService = new ExerciseService(exerciseRepository);
        WorkoutService workoutService = new WorkoutService(workoutRepository, exerciseService);
        AuthenticationService authService = new AuthenticationService(userService, passwordHasher);

        new AppPanel(authService,
                     workoutService,
                     exerciseService,
                     userService,
                     inputReader).showPanel();
    }
}
