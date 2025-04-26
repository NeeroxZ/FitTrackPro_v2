package app;

import adapters.cli.ConsoleInputReader;

import adapters.session.LoggedInUser;
import core.ports.session.IUserSessionService;
import core.usecase.exercise.GetExercisesUseCase;
import core.usecase.user.AuthenticationUserUseCase;
import core.usecase.user.UserUseCaseFactory;
import core.usecase.workout.WorkoutUseCaseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceFactoryTest {

    private ServiceFactory serviceFactory;

    @BeforeEach
    void setUp() {
        serviceFactory = new ServiceFactory();
    }

    @Test
    @DisplayName("Konstruktor sollte alle notwendigen Services und Repositories initialisieren")
    void constructorShouldInitializeAllServicesAndRepositories() {
        assertNotNull(serviceFactory.getWorkoutUseCaseFactory());
        assertNotNull(serviceFactory.getUserUseCaseFactory());
        assertNotNull(serviceFactory.getUserSessionService());

        assertTrue(serviceFactory.getUserSessionService() instanceof LoggedInUser,
                   "userSessionService sollte eine Instanz von LoggedInUser sein");

        assertNotNull(serviceFactory.getUserUseCaseFactory(), "UserUseCaseFactory sollte initialisiert sein");
        assertNotNull(serviceFactory.getWorkoutUseCaseFactory(), "WorkoutUseCaseFactory sollte initialisiert sein");

        assertTrue(serviceFactory.getUserUseCaseFactory() instanceof UserUseCaseFactory,
                   "getUserUseCaseFactory sollte eine Instanz von UserUseCaseFactory zurückgeben");
        assertTrue(serviceFactory.getWorkoutUseCaseFactory() instanceof WorkoutUseCaseFactory,
                   "getWorkoutUseCaseFactory sollte eine Instanz von WorkoutUseCaseFactory zurückgeben");
    }

    @Test
    @DisplayName("Getter sollten dieselbe Instanz zurückgeben, die im Konstruktor erstellt wurde")
    void gettersShouldReturnSameInstanceCreatedInConstructor() {
        IUserSessionService session1 = serviceFactory.getUserSessionService();
        IUserSessionService session2 = serviceFactory.getUserSessionService();
        assertSame(session1, session2, "getUserSessionService sollte immer dieselbe Instanz zurückgeben");

        WorkoutUseCaseFactory workoutFactory1 = serviceFactory.getWorkoutUseCaseFactory();
        WorkoutUseCaseFactory workoutFactory2 = serviceFactory.getWorkoutUseCaseFactory();
        assertSame(workoutFactory1, workoutFactory2, "getWorkoutUseCaseFactory sollte immer dieselbe Instanz zurückgeben");

        UserUseCaseFactory userFactory1 = serviceFactory.getUserUseCaseFactory();
        UserUseCaseFactory userFactory2 = serviceFactory.getUserUseCaseFactory();
        assertSame(userFactory1, userFactory2, "getUserUseCaseFactory sollte immer dieselbe Instanz zurückgeben");
    }

    @Test
    @DisplayName("createAuthService sollte eine neue Instanz von AuthenticationUserUseCase zurückgeben")
    void createAuthServiceShouldReturnNewAuthenticationUserUseCase() {
        AuthenticationUserUseCase authService1 = serviceFactory.createAuthService();
        AuthenticationUserUseCase authService2 = serviceFactory.createAuthService();

        assertNotNull(authService1, "Die erste Instanz von AuthenticationUserUseCase sollte nicht null sein");
        assertNotNull(authService2, "Die zweite Instanz von AuthenticationUserUseCase sollte nicht null sein");

        assertTrue(true,
                   "createAuthService sollte eine Instanz von AuthenticationUserUseCase zurückgeben");

        assertNotSame(authService1, authService2,
                      "Jeder Aufruf von createAuthService sollte eine neue Instanz zurückgeben");
    }

    @Test
    @DisplayName("createExerciseService sollte eine neue Instanz von GetExercisesUseCase zurückgeben")
    void createExerciseServiceShouldReturnNewGetExercisesUseCase() {
        GetExercisesUseCase exerciseService1 = serviceFactory.createExerciseService();
        GetExercisesUseCase exerciseService2 = serviceFactory.createExerciseService();

        assertNotNull(exerciseService1, "Die erste Instanz von GetExercisesUseCase sollte nicht null sein");
        assertNotNull(exerciseService2, "Die zweite Instanz von GetExercisesUseCase sollte nicht null sein");

        assertTrue(true,
                   "createExerciseService sollte eine Instanz von GetExercisesUseCase zurückgeben");

        assertNotSame(exerciseService1, exerciseService2,
                      "Jeder Aufruf von createExerciseService sollte eine neue Instanz zurückgeben");
    }

    @Test
    @DisplayName("createInputReader sollte eine neue Instanz von ConsoleInputReader zurückgeben")
    void createInputReaderShouldReturnNewConsoleInputReader() {
        ConsoleInputReader inputReader1 = (ConsoleInputReader) serviceFactory.createInputReader();
        ConsoleInputReader inputReader2 = (ConsoleInputReader) serviceFactory.createInputReader();

        assertNotNull(inputReader1, "Die erste Instanz von ConsoleInputReader sollte nicht null sein");
        assertNotNull(inputReader2, "Die zweite Instanz von ConsoleInputReader sollte nicht null sein");

        assertInstanceOf(ConsoleInputReader.class, inputReader1, "createInputReader sollte eine Instanz von ConsoleInputReader zurückgeben");

        assertNotSame(inputReader1, inputReader2,
                      "Jeder Aufruf von createInputReader sollte eine neue Instanz zurückgeben");
    }
}