package core.usecase.exercise;

import core.domain.exercise.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.*;

class CreateExerciseUseCaseTest {

    private TestObjectBuilder b;
    private CreateExerciseUseCase useCase;

    @BeforeEach
    void setUp() {
        b = new TestObjectBuilder();
        useCase = new CreateExerciseUseCase(b.exerciseRepo);
    }

    @Test
    void shouldAddExerciseToRepository() {
        Exercise ex = new Exercise(10, "Pull‑Ups", ExerciseCategory.RUECKEN, Difficulty.HARD, "Klimmzüge", java.util.List.of(MuscleGroup.BACK));
        useCase.create(ex);
        assertEquals(1, b.exerciseRepo.count());
        assertTrue(b.exerciseRepo.findById(10).isPresent());
    }

    @Test
    void shouldOverwriteExerciseWithSameId() {
        Exercise ex1 = new Exercise(1, "Bench", ExerciseCategory.BRUST, Difficulty.MEDIUM, "", java.util.List.of(MuscleGroup.CHEST));
        Exercise ex2 = new Exercise(1, "Bench Wide", ExerciseCategory.BRUST, Difficulty.MEDIUM, "", java.util.List.of(MuscleGroup.CHEST));
        useCase.create(ex1);
        useCase.create(ex2);
        assertEquals(1, b.exerciseRepo.count());
        assertEquals("Bench Wide", b.exerciseRepo.findById(1).orElseThrow().name());
    }
}
