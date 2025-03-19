package app;

import core.ports.repository.IExerciseRepository;
import adapters.persistence.inmemory.InMemoryExerciseRepository;
import adapters.persistence.inmemory.InMemoryWorkoutRepository;
import core.ports.repository.IWorkoutRepository;
import core.usecase.exercise.GetExercisesUseCase;
import adapters.cli.ConsoleInputReader;
import adapters.cli.InputReader;
import core.ports.services.IPasswordHasher;
import adapters.persistence.inmemory.InMemoryUserRepository;
import core.ports.repository.IUserRepository;
import core.ports.session.IUserSessionService;
import core.ports.workout.IWorkoutGenerator;
import adapters.security.SHA256PasswordHasher;
import adapters.session.LoggedInUser;
import adapters.workout.SmarterWorkoutGenerator;
import core.usecase.user.AuthenticationUserUseCase;
import core.usecase.user.UserUseCaseFactory;
import core.usecase.workout.WorkoutUseCaseFactory;

import java.util.Scanner;

public class ServiceFactory {
    private final IPasswordHasher passwordHasher;
    private final IUserRepository userRepository;
    private final IExerciseRepository exerciseRepository;
    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;
    private final IWorkoutGenerator workoutGenerator;
    private final WorkoutUseCaseFactory workoutUseCaseFactory;
    private final UserUseCaseFactory userUseCaseFactory;

    public ServiceFactory() {
        this.passwordHasher = new SHA256PasswordHasher();
        this.userRepository = new InMemoryUserRepository();
        this.exerciseRepository = new InMemoryExerciseRepository();
        this.workoutRepository = new InMemoryWorkoutRepository();
        this.userSessionService = new LoggedInUser();
        this.workoutGenerator = new SmarterWorkoutGenerator(exerciseRepository);
        this.workoutUseCaseFactory = new WorkoutUseCaseFactory(workoutRepository, userSessionService, workoutGenerator);
        this.userUseCaseFactory = new UserUseCaseFactory(userRepository,passwordHasher,userSessionService);
    }

    public WorkoutUseCaseFactory getWorkoutUseCaseFactory() {
        return workoutUseCaseFactory;
    }

    public UserUseCaseFactory getUserUseCaseFactory()
    {
        return userUseCaseFactory;
    }

    public IUserSessionService getUserSessionService()
    {
        return userSessionService;
    }

    public AuthenticationUserUseCase createAuthService() {
        return new AuthenticationUserUseCase(userRepository, passwordHasher, userSessionService );
    }

    public GetExercisesUseCase createExerciseService() {
        return new GetExercisesUseCase(exerciseRepository);
    }

    public InputReader createInputReader() {
        return new ConsoleInputReader(new Scanner(System.in));
    }

}
