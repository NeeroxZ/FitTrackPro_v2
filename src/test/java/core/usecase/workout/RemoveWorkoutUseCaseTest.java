package core.usecase.workout;

import core.domain.exercise.*;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IWorkoutRepository;
import core.ports.repository.IExerciseRepository;
import core.ports.repository.InMemoryWorkoutRepositoryMock;
import core.ports.repository.InMemoryExerciseRepositoryMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RemoveWorkoutUseCaseTest {

    private RemoveWorkoutUseCase removeWorkoutUseCase;
    private IWorkoutRepository workoutRepository;
    private IExerciseRepository exerciseRepository;

    @BeforeEach
    void setUp() {
        // Initialisiere die Mock-Repositories
        workoutRepository = new InMemoryWorkoutRepositoryMock();
        exerciseRepository = new InMemoryExerciseRepositoryMock();

        // Beispielübungen in das Mock-ExerciseRepository laden
        exerciseRepository.addExercise(new Exercise(1, "Bench Press", ExerciseCategory.BRUST, Difficulty.MEDIUM, "Langhantel drücken.", List.of(MuscleGroup.CHEST)));
        exerciseRepository.addExercise(new Exercise(2, "Squats", ExerciseCategory.BEINE, Difficulty.HARD, "Kniebeugen für starke Beine.", List.of(MuscleGroup.LEGS)));

        removeWorkoutUseCase = new RemoveWorkoutUseCase(workoutRepository);

    }

    @Test
    void shouldRemoveExistingWorkout() {
        // Arrange: Übungen aus dem Repository holen
        List<Exercise> mondayExercises = List.of(
                exerciseRepository.findById(1).orElseThrow(),
                exerciseRepository.findById(2).orElseThrow()
                                                );

        // Ein Workout mit einem Trainingsplan erstellen
        Workout workout = new Workout(
                1,
                "Full Body Workout",
                WorkoutType.KRAFTSPORT,
                List.of(new TrainingDay("Montag", mondayExercises)),
                "user1",
                3,
                TrainingSplit.GANZKORPER
        );

        workoutRepository.saveWorkout(workout);

        // Act: Workout entfernen
        boolean result = removeWorkoutUseCase.removeWorkout(1);

        // Assert: Workout sollte entfernt sein
        assertTrue(result, "Workout sollte entfernt werden");
        assertFalse(workoutRepository.findById(1).isPresent(), "Workout sollte nicht mehr existieren");
    }
}
