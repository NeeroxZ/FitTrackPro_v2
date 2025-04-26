package app;

import core.domain.workout.plans.GanzkoerperPlan;
import core.domain.workout.plans.IWorkoutPlan;
import core.domain.workout.plans.OberUnterPlan;
import core.domain.workout.plans.PushPullLegPlan;
import core.ports.repository.IExerciseRepository;
import adapters.persistence.inmemory.InMemoryExerciseRepository;
import adapters.persistence.inmemory.InMemoryWorkoutRepository;
import core.ports.repository.IWorkoutRepository;
import core.usecase.exercise.GetExercisesUseCase;
import adapters.cli.ConsoleInputReader;
import adapters.cli.IInputReader;
import core.ports.services.IPasswordHasher;
import adapters.persistence.inmemory.InMemoryUserRepository;
import core.ports.repository.IUserRepository;
import core.ports.session.IUserSessionService;
import adapters.security.SHA256PasswordHasher;
import adapters.session.LoggedInUser;
import core.usecase.user.AuthenticationUserUseCase;
import core.usecase.user.UserUseCaseFactory;
import core.usecase.workout.WorkoutUseCaseFactory;

import java.util.List;
import java.util.Scanner;

public class ServiceFactory
{
    private final IPasswordHasher passwordHasher;
    private final IUserRepository userRepository;
    private final IExerciseRepository exerciseRepository;
    private final IUserSessionService userSessionService;
    private final WorkoutUseCaseFactory workoutUseCaseFactory;
    private final UserUseCaseFactory userUseCaseFactory;

    public ServiceFactory()
    {
        List<IWorkoutPlan> plans = List.of(
                new PushPullLegPlan(),
                new OberUnterPlan(),
                new GanzkoerperPlan()
                                          );

        this.passwordHasher = new SHA256PasswordHasher();
        this.userRepository = new InMemoryUserRepository();
        this.exerciseRepository = new InMemoryExerciseRepository();
        IWorkoutRepository workoutRepository = new InMemoryWorkoutRepository();
        this.userSessionService = new LoggedInUser();
        this.workoutUseCaseFactory = new WorkoutUseCaseFactory(workoutRepository, exerciseRepository, userSessionService, plans);
        this.userUseCaseFactory = new UserUseCaseFactory(userRepository, passwordHasher, userSessionService);
    }

    public WorkoutUseCaseFactory getWorkoutUseCaseFactory()
    {
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

    public AuthenticationUserUseCase createAuthService()
    {
        return new AuthenticationUserUseCase(userRepository, passwordHasher, userSessionService);
    }

    public GetExercisesUseCase createExerciseService()
    {
        return new GetExercisesUseCase(exerciseRepository);
    }

    public IInputReader createInputReader()
    {
        return new ConsoleInputReader(new Scanner(System.in));
    }

}
