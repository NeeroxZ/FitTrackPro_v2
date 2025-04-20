package core.usecase.exercise;

import core.domain.exercise.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.*;

class GetExerciseByIdUseCaseTest {

    private TestObjectBuilder b;
    private GetExerciseByIdUseCase useCase;

    @BeforeEach
    void setUp() {
        b = new TestObjectBuilder().withDefaultExercises();
        useCase = new GetExerciseByIdUseCase(b.exerciseRepo);
    }

    @Test
    void returnsExerciseWhenIdExists() {
        var result = useCase.getById(1);
        assertTrue(result.isPresent());
        assertEquals("Bench Press", result.get().name());
    }

    @Test
    void returnsEmptyWhenIdNotFound() {
        assertTrue(useCase.getById(99).isEmpty());
    }
}
