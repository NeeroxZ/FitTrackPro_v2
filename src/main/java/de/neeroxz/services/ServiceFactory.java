package de.neeroxz.services;

import de.neeroxz.exercise.repository.IExerciseRepository;
import de.neeroxz.exercise.repository.InMemoryExerciseRepository;
import de.neeroxz.exercise.repository.InMemoryWorkoutRepository;
import de.neeroxz.exercise.repository.WorkoutRepository;
import de.neeroxz.exercise.service.ExerciseService;
import de.neeroxz.exercise.service.WorkoutService;
import de.neeroxz.input.ConsoleInputReader;
import de.neeroxz.input.InputReader;
import de.neeroxz.security.PasswordHasher;
import de.neeroxz.user.repository.InMemoryUserRepository;
import de.neeroxz.user.service.UserRepository;
import de.neeroxz.user.service.UserService;
import de.neeroxz.util.SHA256PasswordHasher;

import java.util.Scanner;

public class ServiceFactory {
    private final PasswordHasher passwordHasher;
    private final UserRepository userRepository;
    private final IExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;

    public ServiceFactory() {
        // Hier definierst du die Abhängigkeiten
        this.passwordHasher = new SHA256PasswordHasher();
        this.userRepository = new InMemoryUserRepository();
        this.exerciseRepository = new InMemoryExerciseRepository();
        this.workoutRepository = new InMemoryWorkoutRepository();
    }

    public AuthenticationService createAuthService() {
        UserService userService = new UserService(userRepository);
        return new AuthenticationService(userService, passwordHasher);
    }

    public ExerciseService createExerciseService() {
        return new ExerciseService(exerciseRepository);
    }

    public WorkoutService createWorkoutService() {
        ExerciseService exerciseService = createExerciseService();
        return new WorkoutService(workoutRepository, exerciseService);
    }

    public InputReader createInputReader() {
        return new ConsoleInputReader(new Scanner(System.in));
    }
}
