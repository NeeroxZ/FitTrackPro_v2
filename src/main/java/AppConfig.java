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
import de.neeroxz.user.repository.InMemoryUserRepository;
import de.neeroxz.user.service.UserRepository;
import de.neeroxz.user.service.UserService;
import de.neeroxz.util.SHA256PasswordHasher;

import java.util.Scanner;

public class AppConfig {
    public static AuthenticationService createAuthService() {
        PasswordHasher passwordHasher = new SHA256PasswordHasher();
        UserRepository userRepository = new InMemoryUserRepository();
        UserService userService = new UserService(userRepository);
        return new AuthenticationService(userService, passwordHasher);
    }

    public static WorkoutService createWorkoutService(ExerciseService exerciseService) {
        WorkoutRepository workoutRepository = new InMemoryWorkoutRepository();
        return new WorkoutService(workoutRepository, exerciseService);
    }

    public static ExerciseService createExerciseService() {
        IExerciseRepository exerciseRepository = new InMemoryExerciseRepository();
        return new ExerciseService(exerciseRepository);
    }

    public static InputReader createInputReader() {
        return new ConsoleInputReader(new Scanner(System.in));
    }
}
