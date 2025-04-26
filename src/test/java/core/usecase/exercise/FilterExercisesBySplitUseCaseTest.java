package core.usecase.exercise;

import core.domain.exercise.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterExercisesBySplitUseCaseTest {

    private TestObjectBuilder b;
    private FilterExercisesBySplitUseCase useCase;

    @BeforeEach
    void setUp() {
        b = new TestObjectBuilder();
        // neun typische Übungen – je eine pro Kategorie
        b.withExercise(new Exercise(1, "Bench",          ExerciseCategory.BRUST,               Difficulty.MEDIUM, "", List.of(MuscleGroup.CHEST)))
         .withExercise(new Exercise(2, "Row",            ExerciseCategory.RUECKEN,             Difficulty.MEDIUM, "", List.of(MuscleGroup.BACK)))
         .withExercise(new Exercise(3, "Lateral Raise",  ExerciseCategory.SEITLICHE_SCHULTER,  Difficulty.EASY,   "", List.of(MuscleGroup.SHOULDERS)))
         .withExercise(new Exercise(4, "Face Pull",      ExerciseCategory.HINTERE_SCHULTER,    Difficulty.EASY,   "", List.of(MuscleGroup.SHOULDERS)))
         .withExercise(new Exercise(5, "OH Press",       ExerciseCategory.VORDERE_SCHULTER,    Difficulty.MEDIUM, "", List.of(MuscleGroup.SHOULDERS)))
         .withExercise(new Exercise(6, "Curl",           ExerciseCategory.BIZEPS,              Difficulty.EASY,   "", List.of(MuscleGroup.ARMS)))
         .withExercise(new Exercise(7, "Dip",            ExerciseCategory.TRIZEPS,             Difficulty.MEDIUM, "", List.of(MuscleGroup.ARMS)))
         .withExercise(new Exercise(8, "Squat",          ExerciseCategory.BEINE,               Difficulty.HARD,   "", List.of(MuscleGroup.LEGS)))
         .withExercise(new Exercise(9, "Plank",          ExerciseCategory.CORE,                Difficulty.EASY,   "", List.of(MuscleGroup.CORE)));

        useCase = new FilterExercisesBySplitUseCase(b.exerciseRepo);
    }

    @Test
    void ganztkoerperReturnsAll() {
        List<Exercise> result = useCase.filterBySplit(TrainingSplit.GANZKORPER);
        assertEquals(b.exerciseRepo.count(), result.size());
    }

    @Test
    void pushSplitReturnsCorrectCategories() {
        List<Exercise> result = useCase.filterBySplit(TrainingSplit.PUSH);
        assertEquals(3, result.size());
        assertTrue(result.stream().allMatch(e -> List.of(
                ExerciseCategory.BRUST,
                ExerciseCategory.HINTERE_SCHULTER,
                ExerciseCategory.TRIZEPS).contains(e.category())));
    }

    @Test
    void pullSplitReturnsCorrectCategories() {
        List<Exercise> result = useCase.filterBySplit(TrainingSplit.PULL);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> List.of(
                ExerciseCategory.RUECKEN,
                ExerciseCategory.BIZEPS).contains(e.category())));
    }

    @Test
    void legSplitReturnsCorrectCategories() {
        List<Exercise> result = useCase.filterBySplit(TrainingSplit.LEG);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> List.of(
                ExerciseCategory.BEINE,
                ExerciseCategory.CORE).contains(e.category())));
    }

    @Test
    void oberUnterSplitReturnsCorrectCategories() {
        List<Exercise> result = useCase.filterBySplit(TrainingSplit.OBER_UNTER);
        assertEquals(9, result.size()); // alle neun Kategorien in der Liste
        assertTrue(result.stream().allMatch(e -> List.of(
                ExerciseCategory.BRUST,
                ExerciseCategory.RUECKEN,
                ExerciseCategory.SEITLICHE_SCHULTER,
                ExerciseCategory.VORDERE_SCHULTER,
                ExerciseCategory.HINTERE_SCHULTER,
                ExerciseCategory.BIZEPS,
                ExerciseCategory.TRIZEPS,
                ExerciseCategory.BEINE,
                ExerciseCategory.CORE).contains(e.category())));
    }

    @Test
    void pushPullLegSplitReturnsCorrectCategories() {
        List<Exercise> result = useCase.filterBySplit(TrainingSplit.PUSH_PULL_LEG);
        assertEquals(7, result.size());
        assertTrue(result.stream().allMatch(e -> List.of(
                ExerciseCategory.BRUST,
                ExerciseCategory.HINTERE_SCHULTER,
                ExerciseCategory.TRIZEPS,
                ExerciseCategory.RUECKEN,
                ExerciseCategory.BIZEPS,
                ExerciseCategory.BEINE,
                ExerciseCategory.CORE).contains(e.category())));
    }
}
