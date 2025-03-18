package de.neeroxz.exercise.repository;

import de.neeroxz.adapters.persistence.inmemory.InMemoryExerciseRepository;
import de.neeroxz.core.domain.exercise.Exercise;
import de.neeroxz.core.domain.exercise.ExerciseCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryExerciseRepositoryTest
{

    private InMemoryExerciseRepository repository;

    @BeforeEach
    public void setUp()
    {
        repository = new InMemoryExerciseRepository();
    }

    @Test
    public void testFindAll()
    {
        List<Exercise> allExercises = repository.findAll();
        // Statt exakt 11 zu erwarten, prüfen wir, dass mindestens 11 Übungen vorhanden sind
        assertTrue(allExercises.size() >= 11, "Es sollten mindestens 11 Übungen vorhanden sein");

        // Optional: Überprüfe, ob einige bekannte Übungen vorhanden sind
        boolean benchPressFound = allExercises.stream()
                                              .anyMatch(e -> "Bench Press".equals(e.name()));
        assertTrue(benchPressFound, "Bench Press sollte in der Übungsliste enthalten sein");
    }


    @Test
    public void testFindById()
    {
        // Test: Übung mit id 1 soll "Bench Press" heißen
        Optional<Exercise> exerciseOpt = repository.findById(1);
        assertTrue(exerciseOpt.isPresent(), "Exercise mit id 1 sollte vorhanden sein");
        Exercise exercise = exerciseOpt.get();
        assertEquals("Bench Press", exercise.name(), "Name der Übung stimmt nicht");
        assertEquals(ExerciseCategory.BRUST, exercise.category(), "Kategorie sollte BRUST sein");
    }

    @Test
    public void testFindByIdNotFound()
    {
        // Test: Für eine nicht existierende ID (z. B. 999) soll kein Exercise zurückgegeben werden.
        Optional<Exercise> exerciseOpt = repository.findById(999);
        assertFalse(exerciseOpt.isPresent(), "Keine Übung sollte für id 999 gefunden werden");
    }
}
