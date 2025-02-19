package de.neeroxz.exercise;

/**
 * Class: InMemoryExerciseRepository
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryExerciseRepository implements ExerciseRepository {
    private final List<Exercise> exercises = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public InMemoryExerciseRepository() {
        // 🔥 Initiale Übungen hinzufügen (statt SQL Inserts)
        addExercise("Bench Press", ExerciseCategory.BRUST, "Mittel");
        addExercise("Incline Bench Press", ExerciseCategory.BRUST, "Schwer");
        addExercise("Chest Fly", ExerciseCategory.BRUST, "Leicht");
        addExercise("Push-up", ExerciseCategory.BRUST, "Leicht");
        addExercise("Dips", ExerciseCategory.BRUST, "Mittel");
        addExercise("Jogging", ExerciseCategory.CARDIO, "Mittel");
        addExercise("Cycling", ExerciseCategory.CARDIO, "Mittel");
        addExercise("Swimming", ExerciseCategory.CARDIO, "Schwer");
        addExercise("Mountain Pose", ExerciseCategory.YOGA, "Leicht");
        addExercise("Downward Dog", ExerciseCategory.YOGA, "Mittel");
        addExercise("Tree Pose", ExerciseCategory.YOGA, "Leicht");
    }

    private void addExercise(String name, ExerciseCategory category, String difficulty)
    {
        exercises.add(
                new Exercise(
                        idCounter.getAndIncrement(),
                        name,
                        category,
                        difficulty
                )
        );
    }

    @Override
    public Optional<Exercise> findById(int id)
    {
        return exercises
                .stream()
                .filter(e -> e.id() == id)
                .findFirst();
    }

    @Override
    public List<Exercise> findAll()
    {
        return new ArrayList<>(exercises);
    }
}
