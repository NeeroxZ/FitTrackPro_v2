package core.usecase.exercise;

import core.domain.exercise.*;
import core.domain.workout.WorkoutType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GetExercisesUseCaseTest {

    private TestObjectBuilder b;
    private GetExercisesUseCase useCase;

    @BeforeEach
    void setUp() {
        b = new TestObjectBuilder();

        // Drei Beispielübungen
        b.withExercise(new Exercise(1, "Bench", ExerciseCategory.BRUST, Difficulty.MEDIUM, "", List.of(MuscleGroup.CHEST)))
         .withExercise(new Exercise(2, "Row", ExerciseCategory.RUECKEN, Difficulty.MEDIUM, "", List.of(MuscleGroup.BACK)))
         .withExercise(new Exercise(3, "Squat", ExerciseCategory.BEINE, Difficulty.HARD, "", List.of(MuscleGroup.LEGS)));

        useCase = new GetExercisesUseCase(b.exerciseRepo);
    }

    @Test
    void getExerciseByIdReturnsCorrectExercise() {
        Optional<Exercise> result = useCase.getExerciseById(1);
        assertTrue(result.isPresent());
        assertEquals("Bench", result.get().name());
    }

    @Test
    void getAllExercisesReturnsAllStored() {
        List<Exercise> all = useCase.getAllExercises();
        assertEquals(3, all.size());
    }

    @Test
    void createExerciseAddsExerciseToRepo() {
        Exercise newExercise = new Exercise(99, "Crunches", ExerciseCategory.CORE, Difficulty.EASY, "", List.of(MuscleGroup.CORE));
        useCase.createExercise(newExercise);
        assertEquals(4, b.exerciseRepo.count());
        assertTrue(b.exerciseRepo.findById(99).isPresent());
    }

    @Test
    void getExercisesByTypeReturnsCorrectOnKraftsport() {
        List<Exercise> kraft = useCase.getExercisesByType(WorkoutType.KRAFTSPORT);
        assertEquals(3, kraft.size());
        assertTrue(kraft.stream().allMatch(e -> e.category().getWorkoutType() == WorkoutType.KRAFTSPORT));
    }

    @Test
    void filterBySplitReturnsOnlyRelevantExercises() {
        List<Exercise> result = useCase.filterExercisesBySplit(TrainingSplit.LEG);
        assertEquals(1, result.size());
        assertEquals(ExerciseCategory.BEINE, result.get(0).category());
    }
}
