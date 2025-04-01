package core.usecase.workout;

import adapters.security.SHA256PasswordHasher;
import core.domain.exercise.TrainingSplit;
import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IWorkoutRepository;
import core.ports.repository.InMemoryWorkoutRepositoryMock;
import core.ports.session.IUserSessionService;
import core.ports.session.InMemoryUserSessionServiceMock;
import core.ports.workout.IWorkoutGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateWorkoutUseCaseTest {

    private IWorkoutRepository workoutRepository;
    private IUserSessionService userSessionService;
    private IWorkoutGenerator workoutGenerator;
    private CreateWorkoutUseCase useCase;

    @BeforeEach
    void setUp() {
        workoutRepository = new InMemoryWorkoutRepositoryMock();
        userSessionService = new InMemoryUserSessionServiceMock();

        workoutGenerator = new IWorkoutGenerator() {
            @Override
            public List<Workout> generateWorkout(String name, WorkoutType type, TrainingSplit split, int frequency, String username) {
                List<Workout> workouts = new ArrayList<>();
                workouts.add(new Workout(1, name, type, new ArrayList<>(), username, frequency, split));
                return workouts;
            }
        };

        useCase = new CreateWorkoutUseCase(workoutRepository, userSessionService, workoutGenerator);
    }

    @Test
    void testCreateWorkout_Success() {
        // Arrange: Einen Testbenutzer in den Session-Service setzen.
        // Hier könnte man auch einen Stub für den IUserSessionService verwenden,
        // um das Verhalten des Session-Managements zu isolieren.
        User testUser = new User(
                "testuser",
                Password.hash("test", new SHA256PasswordHasher()),
                75.0,
                1.80,
                new Birthday(LocalDate.of(1990, 5, 15))
        );
        ((InMemoryUserSessionServiceMock) userSessionService).setLoggedInUser(testUser);

        // Act: Den Use Case ausführen.
        List<Workout> workouts = useCase.createWorkout("My Workout", WorkoutType.KRAFTSPORT, 3, TrainingSplit.GANZKORPER);

        // Assert: Überprüfen, ob das erzeugte Workout korrekt ist.
        assertNotNull(workouts);
        assertEquals(1, workouts.size());
        Workout workout = workouts.get(0);
        assertEquals("My Workout", workout.name());
        assertEquals("testuser", workout.username());
    }

    @Test
    void testCreateWorkout_NoUser_ThrowsException() {
        // Arrange: Es wird kein Benutzer gesetzt, was einen Fehler provozieren soll.
        // Hier wird kein Stub benötigt, da der InMemoryUserSessionService bereits Optional.empty() zurückgibt.

        // Act & Assert: Der Aufruf sollte eine RuntimeException werfen.
        assertThrows(RuntimeException.class, () ->
                             useCase.createWorkout("My Workout", WorkoutType.KRAFTSPORT, 3, TrainingSplit.GANZKORPER)
                    );
    }
}
