package de.neeroxz.app;

import de.neeroxz.core.ports.repository.IExerciseRepository;
import de.neeroxz.adapters.persistence.inmemory.InMemoryExerciseRepository;
import de.neeroxz.adapters.persistence.inmemory.InMemoryWorkoutRepository;
import de.neeroxz.core.ports.repository.IWorkoutRepository;
import de.neeroxz.core.usecase.exercise.ExerciseService;
import de.neeroxz.adapters.cli.ConsoleInputReader;
import de.neeroxz.adapters.cli.InputReader;
import de.neeroxz.core.ports.services.IPasswordHasher;
import de.neeroxz.adapters.persistence.inmemory.InMemoryUserRepository;
import de.neeroxz.core.ports.repository.IUserRepository;
import de.neeroxz.core.ports.session.IUserSessionService;
import de.neeroxz.core.ports.workout.IWorkoutGenerator;
import de.neeroxz.adapters.security.SHA256PasswordHasher;
import de.neeroxz.adapters.session.LoggedInUser;
import de.neeroxz.adapters.workout.SmarterWorkoutGenerator;
import de.neeroxz.core.usecase.user.AuthenticationUserUseCase;
import de.neeroxz.core.usecase.user.UserService;
import de.neeroxz.core.usecase.workout.WorkoutUseCaseFactory;

import java.util.Scanner;

public class ServiceFactory {
    private final IPasswordHasher passwordHasher;
    private final IUserRepository userRepository;
    private final IExerciseRepository exerciseRepository;
    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;
    private final IWorkoutGenerator workoutGenerator;
    private final WorkoutUseCaseFactory workoutUseCaseFactory;

    public ServiceFactory() {
        this.passwordHasher = new SHA256PasswordHasher();
        this.userRepository = new InMemoryUserRepository();
        this.exerciseRepository = new InMemoryExerciseRepository();
        this.workoutRepository = new InMemoryWorkoutRepository();
        this.userSessionService = new LoggedInUser();
        this.workoutGenerator = new SmarterWorkoutGenerator(exerciseRepository);
        this.workoutUseCaseFactory = new WorkoutUseCaseFactory(workoutRepository, userSessionService, workoutGenerator);
    }

    public WorkoutUseCaseFactory getWorkoutUseCaseFactory() {
        return workoutUseCaseFactory;
    }

    public IUserSessionService getUserSessionService()
    {
        return userSessionService;
    }

    public AuthenticationUserUseCase createAuthService() {
        UserService userService = new UserService(userRepository, userSessionService);
        return new AuthenticationUserUseCase(userService, passwordHasher);
    }

    public ExerciseService createExerciseService() {
        return new ExerciseService(exerciseRepository);
    }

    public InputReader createInputReader() {
        return new ConsoleInputReader(new Scanner(System.in));
    }

}
