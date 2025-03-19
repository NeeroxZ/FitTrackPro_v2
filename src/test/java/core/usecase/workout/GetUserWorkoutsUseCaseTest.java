package core.usecase.workout;

import adapters.security.SHA256PasswordHasher;
import core.domain.exercise.*;
import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IExerciseRepository;
import core.ports.repository.IWorkoutRepository;
import core.ports.repository.InMemoryExerciseRepositoryMock;
import core.ports.repository.InMemoryWorkoutRepositoryMock;
import core.ports.session.IUserSessionService;
import core.ports.session.InMemoryUserSessionServiceMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GetUserWorkoutsUseCaseTest {

    private IExerciseRepository exerciseRepository;
    private IWorkoutRepository workoutRepository;
    private IUserSessionService userSessionService;
    private GetUserWorkoutsUseCase getUserWorkoutsUseCase;
    private User testUser;
    @BeforeEach
    void setUp() {
        workoutRepository = new InMemoryWorkoutRepositoryMock();
        exerciseRepository = new InMemoryExerciseRepositoryMock();
        userSessionService = new InMemoryUserSessionServiceMock(); // ✅ Session Mock initialisieren

        // ✅ Benutzer setzen
        testUser = new User(
                "test",
                Password.hash("test", new SHA256PasswordHasher()),
                75.0,
                1.80,
                new Birthday(LocalDate.of(1990, 5, 15)));

        userSessionService.setLoggedInUser(testUser);

        // Beispielübungen ins Repository laden
        exerciseRepository.addExercise(new Exercise(1, "Bench Press", ExerciseCategory.BRUST, Difficulty.MEDIUM, "Langhantel drücken.", List.of(MuscleGroup.CHEST)));
        exerciseRepository.addExercise(new Exercise(2, "Squats", ExerciseCategory.BEINE, Difficulty.HARD, "Kniebeugen für starke Beine.", List.of(MuscleGroup.LEGS)));

        List<Exercise> mondayExercises = List.of(
                exerciseRepository.findById(1).orElseThrow(),
                exerciseRepository.findById(2).orElseThrow()
                                                );

        // Workout für den Benutzer hinzufügen
        Workout workout = new Workout(
                1,
                "Full Body Workout",
                WorkoutType.KRAFTSPORT,
                List.of(new TrainingDay("Montag", mondayExercises)),
                "user1", // ✅ Workout gehört zu user1
                3,
                TrainingSplit.GANZKORPER
        );

        workoutRepository.saveWorkout(workout);

        // Use Case initialisieren
        getUserWorkoutsUseCase = new GetUserWorkoutsUseCase(workoutRepository, userSessionService);
    }

    @Test
    void shouldReturnEmptyListIfNoWorkoutsExist() {
        // Arrange: Benutzer wechseln
        userSessionService.setLoggedInUser(testUser);

        // Act: Workouts abrufen
        List<Workout> userWorkouts = getUserWorkoutsUseCase.getUserWorkouts();

        // Assert: Es sollte keine Workouts für user2 geben
        assertTrue(userWorkouts.isEmpty(), "Es sollten keine Workouts für user2 existieren.");
    }
}