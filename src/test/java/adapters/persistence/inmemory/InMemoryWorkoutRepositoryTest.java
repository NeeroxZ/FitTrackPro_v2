package adapters.persistence.inmemory;

import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.TrainingDay;
import core.domain.exercise.Difficulty;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.domain.exercise.MuscleGroup;
import core.domain.exercise.TrainingSplit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryWorkoutRepositoryTest {

    private InMemoryWorkoutRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryWorkoutRepository();
    }

    private Workout createSampleWorkout(String username) {
        Exercise e = new Exercise(1, "Push-up", ExerciseCategory.BRUST, Difficulty.EASY, "Push-up", List.of(MuscleGroup.CHEST));
        TrainingDay day = new TrainingDay("Tag 1", List.of(e));
        return new Workout(0, "Mein Workout", WorkoutType.KRAFTSPORT, List.of(day), username, 3, TrainingSplit.GANZKORPER);
    }

    @Test
    void shouldSaveWorkout_andAssignId() {
        repository.saveWorkout(createSampleWorkout("user1"));
        List<Workout> all = repository.findAll();
        assertEquals(1, all.size());
        assertEquals("user1", all.get(0).username());
        assertTrue(all.get(0).id() > 0); // id wurde vergeben
    }

    @Test
    void shouldFindById_whenWorkoutExists() {
        repository.saveWorkout(createSampleWorkout("user2"));
        int id = repository.findAll().get(0).id();

        Optional<Workout> found = repository.findById(id);
        assertTrue(found.isPresent());
        assertEquals("user2", found.get().username());
    }

    @Test
    void shouldReturnEmpty_whenWorkoutNotFound() {
        assertTrue(repository.findById(999).isEmpty());
    }

    @Test
    void shouldFindByUser() {
        repository.saveWorkout(createSampleWorkout("alice"));
        repository.saveWorkout(createSampleWorkout("bob"));
        repository.saveWorkout(createSampleWorkout("alice"));

        List<Workout> aliceWorkouts = repository.findByUser("alice");
        List<Workout> bobWorkouts = repository.findByUser("bob");

        assertEquals(2, aliceWorkouts.size());
        assertEquals(1, bobWorkouts.size());
    }

    @Test
    void shouldDeleteById_andReturnTrue() {
        repository.saveWorkout(createSampleWorkout("testuser"));
        int id = repository.findAll().get(0).id();

        boolean deleted = repository.deleteById(id);
        assertTrue(deleted);
        assertTrue(repository.findById(id).isEmpty());
    }

    @Test
    void shouldReturnFalse_whenDeleteNonExistingId() {
        boolean result = repository.deleteById(123456);
        assertFalse(result);
    }
}
