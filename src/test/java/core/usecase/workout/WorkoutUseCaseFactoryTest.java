package core.usecase.workout;

import core.domain.exercise.TrainingSplit;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IWorkoutRepository;
import core.ports.repository.InMemoryWorkoutRepositoryMock;
import core.ports.session.IUserSessionService;
import core.ports.session.InMemoryUserSessionServiceMock;
import core.ports.workout.IWorkoutGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkoutUseCaseFactoryTest {

    private IWorkoutRepository workoutRepository;
    private IUserSessionService userSessionService;
    private IWorkoutGenerator workoutGenerator;
    private WorkoutUseCaseFactory factory;

    class DummyWorkoutGenerator implements IWorkoutGenerator {
        @Override
        public List<Workout> generateWorkout(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username)
        {
            return List.of();
        }
        // Implementiere nur die Methoden, die im Test benötigt werden.
    }
    @BeforeEach
    void setUp() {
        // Erstellen von Mock-Objekten für die Abhängigkeiten
        workoutRepository = new InMemoryWorkoutRepositoryMock();
        userSessionService = new InMemoryUserSessionServiceMock();
        workoutGenerator = new DummyWorkoutGenerator();
        // Instanziierung der Factory mit den Mocks
        factory = new WorkoutUseCaseFactory(workoutRepository, userSessionService, workoutGenerator);
    }

    @Test
    void createWorkoutUseCase() {
        CreateWorkoutUseCase createWorkoutUseCase = factory.createWorkoutUseCase();
        assertNotNull(createWorkoutUseCase, "createWorkoutUseCase sollte eine Instanz zurückgeben");
        // Optional: Überprüfen, ob die Abhängigkeiten korrekt injiziert wurden.
        // Das geht über Reflection, falls die Felder nicht öffentlich sind.
        assertTrue(createWorkoutUseCase instanceof CreateWorkoutUseCase, "Erwarteter Typ ist CreateWorkoutUseCase");
    }

    @Test
    void getUserWorkoutsUseCase() {
        GetUserWorkoutsUseCase getUserWorkoutsUseCase = factory.getUserWorkoutsUseCase();
        assertNotNull(getUserWorkoutsUseCase, "getUserWorkoutsUseCase sollte eine Instanz zurückgeben");
        assertTrue(getUserWorkoutsUseCase instanceof GetUserWorkoutsUseCase, "Erwarteter Typ ist GetUserWorkoutsUseCase");
    }

    @Test
    void removeWorkoutUseCase() {
        RemoveWorkoutUseCase removeWorkoutUseCase = factory.removeWorkoutUseCase();
        assertNotNull(removeWorkoutUseCase, "removeWorkoutUseCase sollte eine Instanz zurückgeben");
        assertTrue(removeWorkoutUseCase instanceof RemoveWorkoutUseCase, "Erwarteter Typ ist RemoveWorkoutUseCase");
    }
}
