package de.neeroxz.exercise.repository;

import de.neeroxz.exercise.model.Workout;
import de.neeroxz.exercise.model.WorkoutType;
import de.neeroxz.exercise.model.TrainingSplit;
import de.neeroxz.exercise.model.Exercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryWorkoutRepositoryTest
{

    private InMemoryWorkoutRepository repository;

    @BeforeEach
    public void setup()
    {
        repository = new InMemoryWorkoutRepository();
    }

    @Test
    public void testSaveWorkoutAndAutoIncrementId()
    {
        // Erstelle ein Dummy-Workout ohne Übungen (leere Liste)
        List<Exercise> exercises = new ArrayList<>();
        Workout workout = new Workout(
                0,
                "Test Workout",
                WorkoutType.KRAFTSPORT,
                exercises,
                "testuser",
                3,
                TrainingSplit.PUSH
        );
        repository.saveWorkout(workout);

        // Das Workout sollte nun eine automatisch vergebene ID haben (z.B. 1)
        Optional<Workout> retrieved = repository.findById(1);
        assertTrue(retrieved.isPresent(), "Workout sollte gefunden werden");
        assertEquals(1, retrieved.get().id(), "ID muss automatisch gesetzt werden");
        assertEquals("Test Workout", retrieved.get().name(), "Name des Workouts stimmt nicht");
    }

    @Test
    public void testFindByUser()
    {
        List<Exercise> exercises = new ArrayList<>();
        Workout workout1 = new Workout(
                0,
                "Workout 1",
                WorkoutType.CARDIO,
                exercises,
                "user1",
                3,
                TrainingSplit.GANZKORPER
        );
        Workout workout2 = new Workout(
                0,
                "Workout 2",
                WorkoutType.YOGA,
                exercises,
                "user1",
                4,
                TrainingSplit.OBER_UNTER
        );
        Workout workout3 = new Workout(
                0,
                "Workout 3",
                WorkoutType.KRAFTSPORT,
                exercises,
                "user2",
                3,
                TrainingSplit.PUSH
        );
        repository.saveWorkout(workout1);
        repository.saveWorkout(workout2);
        repository.saveWorkout(workout3);

        List<Workout> user1Workouts = repository.findByUser("user1");
        assertEquals(2, user1Workouts.size(), "Es sollten 2 Workouts für user1 gefunden werden");
        user1Workouts.forEach(w -> assertEquals("user1", w.username()));
    }

    @Test
    public void testFindAll()
    {
        List<Exercise> exercises = new ArrayList<>();
        Workout workout1 = new Workout(
                0,
                "Workout 1",
                WorkoutType.CARDIO,
                exercises,
                "user1",
                3,
                TrainingSplit.GANZKORPER
        );
        Workout workout2 = new Workout(
                0,
                "Workout 2",
                WorkoutType.YOGA,
                exercises,
                "user1",
                4,
                TrainingSplit.OBER_UNTER
        );
        repository.saveWorkout(workout1);
        repository.saveWorkout(workout2);

        List<Workout> allWorkouts = repository.findAll();
        assertEquals(2, allWorkouts.size(), "findAll() sollte 2 Workouts zurückgeben");
    }

    @Test
    public void testDeleteById()
    {
        List<Exercise> exercises = new ArrayList<>();
        Workout workout = new Workout(
                0,
                "Workout zum Löschen",
                WorkoutType.CARDIO,
                exercises,
                "user1",
                3,
                TrainingSplit.GANZKORPER
        );
        repository.saveWorkout(workout); // bekommt ID 1
        repository.deleteById(1);
        Optional<Workout> retrieved = repository.findById(1);
        assertFalse(retrieved.isPresent(), "Workout sollte nach dem Löschen nicht mehr vorhanden sein");
    }
}
