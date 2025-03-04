package de.neeroxz.exercise.repository;

/**
 * Class: InMemoryWorkoutRepository
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */
import de.neeroxz.exercise.model.Workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryWorkoutRepository implements WorkoutRepository
{
    private final List<Workout> workouts = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1); // Simuliert Auto-Increment IDs

    @Override
    public void saveWorkout(Workout workout)
    {
        int id = idCounter.getAndIncrement(); // Auto-Increment ID vergeben
        Workout storedWorkout = new Workout(
                id,
                workout.name(),
                workout.type(),
                workout.exercises(),
                workout.username(),
                workout.frequency(),
                workout.split()
        );
        workouts.add(storedWorkout);
        System.out.println("✅ Workout gespeichert: " + storedWorkout);
    }

    @Override
    public List<Workout> findAll()
    {
        return new ArrayList<>(workouts); // Gibt eine Kopie zurück, um Manipulation zu vermeiden
    }

    @Override
    public Optional<Workout> findById(int id)
    {
        return workouts
                .stream()
                .filter(w -> w.id() == id)
                .findFirst();
    }

    @Override
    public List<Workout> findByUser(String username)
    {
        return workouts
                .stream()
                .filter(w -> w.username().equals(username))
                .toList();
    }

    @Override
    public void deleteById(int id)
    {
        this.workouts.removeIf(w -> w.id() == id);
        System.out.println("✅ Workout gelöscht (ID: " + id + ")");
    }
}
