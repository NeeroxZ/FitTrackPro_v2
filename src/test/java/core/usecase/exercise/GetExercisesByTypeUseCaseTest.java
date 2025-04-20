package core.usecase.exercise;

import core.domain.exercise.*;
import core.domain.workout.WorkoutType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetExercisesByTypeUseCaseTest {

    private TestObjectBuilder b;
    private GetExercisesByTypeUseCase useCase;

    @BeforeEach
    void setUp() {
        b = new TestObjectBuilder();
        b.withExercise(new Exercise(1, "Bench", ExerciseCategory.BRUST, Difficulty.MEDIUM, "", List.of(MuscleGroup.CHEST)))
         .withExercise(new Exercise(2, "Row", ExerciseCategory.RUECKEN, Difficulty.MEDIUM, "", List.of(MuscleGroup.BACK)))
         .withExercise(new Exercise(3, "Running", ExerciseCategory.CARDIO, Difficulty.EASY, "", List.of()));

        useCase = new GetExercisesByTypeUseCase(b.exerciseRepo);
    }

    @Test
    void returnsKraftsportExercises() {
        var result = useCase.getByType(WorkoutType.KRAFTSPORT);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(e -> e.category().getWorkoutType() == WorkoutType.KRAFTSPORT));
    }

    @Test
    void returnsCardioExercises() {
        var result = useCase.getByType(WorkoutType.CARDIO);
        assertEquals(1, result.size());
        assertEquals("Running", result.get(0).name());
    }

    @Test
    void returnsEmptyForYogaWhenNonePresent() {
        assertTrue(useCase.getByType(WorkoutType.YOGA).isEmpty());
    }
}
