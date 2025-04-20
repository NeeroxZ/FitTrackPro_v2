package adapters.persistence.inmemory;

import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.Difficulty;
import core.domain.exercise.MuscleGroup;
import core.domain.workout.WorkoutType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryExerciseRepositoryTest {

    private InMemoryExerciseRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryExerciseRepository();
    }

    @Test
    void findById_returnsExercise_whenIdExists() {
        Optional<Exercise> result = repository.findById(1);
        assertTrue(result.isPresent());
    }

    @Test
    void findById_returnsEmpty_whenIdDoesNotExist() {
        Optional<Exercise> result = repository.findById(-1);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_returnsListOfExercises() {
        List<Exercise> all = repository.findAll();
        assertFalse(all.isEmpty());
    }

    @Test
    void addExercise_addsExerciseToRepository() {
        Exercise exercise = new Exercise(999, "Test Exercise", ExerciseCategory.CORE, Difficulty.EASY, "Test", List.of(MuscleGroup.CORE));
        repository.addExercise(exercise);

        Optional<Exercise> result = repository.findById(999);
        assertTrue(result.isPresent());
        assertEquals("Test Exercise", result.get().name());
    }

    @Test
    void findByType_returnsFilteredExercises() {
        Optional<List<Exercise>> result = repository.findByType(WorkoutType.CARDIO);
        assertTrue(result.isPresent());
        assertFalse(result.get().isEmpty());
    }

    @Test
    void findByType_returnsEmpty_whenNoMatch() {
        InMemoryExerciseRepository emptyRepo = new InMemoryExerciseRepository();

        // alle initialen Übungen manuell entfernen
        emptyRepo.findAll().forEach(e -> emptyRepo.removeExercise(e.id()));

        Optional<List<Exercise>> result = emptyRepo.findByType(WorkoutType.YOGA); // oder irgendein Typ
        assertTrue(result.isEmpty());
    }


    @Test
    void removeExercise_returnsFalseAlways() {
        assertFalse(repository.removeExercise(123)); // Da es noch nicht implementiert ist
    }
}
