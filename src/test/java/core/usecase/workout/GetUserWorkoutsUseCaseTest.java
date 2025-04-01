package core.usecase.workout;

import adapters.security.SHA256PasswordHasher;
import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.MuscleGroup;
import core.domain.exercise.TrainingDay;
import core.domain.exercise.Difficulty;
import core.domain.exercise.TrainingSplit;
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

import static org.junit.jupiter.api.Assertions.*;

class GetUserWorkoutsUseCaseTest {

    private IExerciseRepository exerciseRepository;
    private IWorkoutRepository workoutRepository;
    private IUserSessionService userSessionService;
    private GetUserWorkoutsUseCase getUserWorkoutsUseCase;
    private User testUser;

    @BeforeEach
    void setUp() {
        // Verwende InMemory-Implementierungen als einfache Fakes
        workoutRepository = new InMemoryWorkoutRepositoryMock();
        exerciseRepository = new InMemoryExerciseRepositoryMock();
        userSessionService = new InMemoryUserSessionServiceMock();

        // Testbenutzer anlegen und in der Session setzen
        testUser = new User(
                "test",
                Password.hash("test", new SHA256PasswordHasher()),
                75.0,
                1.80,
                new Birthday(LocalDate.of(1990, 5, 15))
        );
        userSessionService.setLoggedInUser(testUser);

        // Beispielübungen in das Repository laden (könnten auch in anderen Tests benötigt werden)
        exerciseRepository.addExercise(new Exercise(1, "Bench Press", ExerciseCategory.BRUST, Difficulty.MEDIUM, "Langhantel drücken.", List.of(MuscleGroup.CHEST)));
        exerciseRepository.addExercise(new Exercise(2, "Squats", ExerciseCategory.BEINE, Difficulty.HARD, "Kniebeugen für starke Beine.", List.of(MuscleGroup.LEGS)));

        // Ein Workout anlegen, das einem anderen Benutzer zugeordnet ist
        List<Exercise> mondayExercises = List.of(
                exerciseRepository.findById(1).orElseThrow(),
                exerciseRepository.findById(2).orElseThrow()
                                                );
        Workout workout = new Workout(
                1,
                "Full Body Workout",
                WorkoutType.KRAFTSPORT,
                List.of(new TrainingDay("Montag", mondayExercises)),
                "user1", // Dieses Workout gehört nicht zum Testbenutzer ("test")
                3,
                TrainingSplit.GANZKORPER
        );
        workoutRepository.saveWorkout(workout);

        // Use Case initialisieren
        getUserWorkoutsUseCase = new GetUserWorkoutsUseCase(workoutRepository, userSessionService);
    }

    @AfterEach
    void tearDown() {
        // Falls notwendig: Zustand der InMemory-Repositories zurücksetzen
    }

    @Test
    void shouldReturnEmptyListForUserWithoutWorkouts() {
        // Act: Workouts für den aktuell eingeloggten Benutzer (test) abrufen
        List<Workout> userWorkouts = getUserWorkoutsUseCase.getUserWorkouts();

        // Assert: Da der Testbenutzer keine Workouts hat, muss die Liste leer sein.
        assertTrue(userWorkouts.isEmpty(), "Es sollten keine Workouts für den Testbenutzer existieren.");
    }

    @Test
    void shouldReturnWorkoutsForUserWithWorkouts() {
        //  Ein Workout für den Testbenutzer anlegen
        List<Exercise> mondayExercises = List.of(
                exerciseRepository.findById(1).orElseThrow(),
                exerciseRepository.findById(2).orElseThrow()
                                                );
        Workout userWorkout = new Workout(
                2,
                "User Workout",
                WorkoutType.KRAFTSPORT,
                List.of(new TrainingDay("Dienstag", mondayExercises)),
                testUser.username(), // Workout gehört jetzt zum Testbenutzer ("test")
                4,
                TrainingSplit.GANZKORPER
        );
        workoutRepository.saveWorkout(userWorkout);

        // Act: Workouts abrufen
        List<Workout> userWorkouts = getUserWorkoutsUseCase.getUserWorkouts();
        System.out.println(userWorkouts.get(0));

        // Assert: Es sollten nun genau 1 Workout zurückgegeben werden, das dem Testbenutzer zugeordnet ist.
        assertEquals(1, userWorkouts.size(), "Es sollte genau ein Workout für den Testbenutzer existieren.");
        assertEquals("User Workout", userWorkouts.get(0).name());
    }
}
