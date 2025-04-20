package adapters.cli.panels;

import adapters.FakeInputReader;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.Exercise;
import core.domain.exercise.Difficulty;
import core.domain.exercise.MuscleGroup;
import core.domain.exercise.TrainingSplit;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.usecase.exercise.GetExercisesUseCase;
import core.usecase.workout.WorkoutUseCaseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExercisePanelTest {

    private TestObjectBuilder builder;
    private FakeInputReader inputReader;
    private ExercisePanel panel;

    @BeforeEach
    void setUp() {
        builder = new TestObjectBuilder().withDefaultUser().withDefaultExercises();

        WorkoutUseCaseFactory factory = new WorkoutUseCaseFactory(
                builder.workoutRepo,
                builder.exerciseRepo,
                builder.sessionMock,
                builder.plans
        );

        GetExercisesUseCase useCase = new GetExercisesUseCase(builder.exerciseRepo);
        inputReader = new FakeInputReader();

        panel = new ExercisePanel(factory, useCase, inputReader);
    }

    @Test
    void testDeleteWorkout_whenWorkoutExists_thenDeletesSuccessfully() {
        // Arrange
        Exercise ex = new Exercise(1, "Test-Übung", ExerciseCategory.BRUST, Difficulty.EASY, "Desc", List.of(MuscleGroup.CHEST));
        Workout workout = new Workout(99, "Test Workout", WorkoutType.KRAFTSPORT, List.of(), "testuser", 3, TrainingSplit.GANZKORPER);
        builder.workoutRepo.saveWorkout(workout);

        // Simuliere Auswahl: 1. Workout zum Löschen
        inputReader.feedInts(1);

        // Act
        panel.deleteWorkout();

        // Assert
        assertTrue(builder.workoutRepo.findAll().isEmpty());
    }

    @Test
    void testShowWorkoutsPanel_whenWorkoutExists_thenDisplaysSuccessfully() {
        // Arrange
        Workout workout = new Workout(42, "Test Workout", WorkoutType.KRAFTSPORT, List.of(), "testuser", 3, TrainingSplit.GANZKORPER);
        builder.workoutRepo.saveWorkout(workout);

        // Simuliere Auswahl: 1. Workout auswählen
        inputReader.feedInts(1);

        // Act + Assert
        assertDoesNotThrow(() -> panel.showWorkoutsPanel());
    }

    @Test
    void testShowWorkoutsPanel_whenInvalidIndex_thenHandlesGracefully() {
        // Arrange
        Workout workout = new Workout(42, "Test Workout", WorkoutType.KRAFTSPORT, List.of(), "testuser", 3, TrainingSplit.GANZKORPER);
        builder.workoutRepo.saveWorkout(workout);

        inputReader.feedInts(999); // ungültiger Index

        // Act + Assert
        assertDoesNotThrow(() -> panel.showWorkoutsPanel());
    }

    @Test
    void testDeleteWorkout_whenNoWorkoutsExist_thenShowsInfo() {
        // Keine Workouts im Repo
        inputReader.feedInts(1); // irrelevant, da sowieso kein Workout vorhanden

        assertDoesNotThrow(() -> panel.deleteWorkout());
    }
}
