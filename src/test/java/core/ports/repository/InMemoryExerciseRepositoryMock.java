package core.ports.repository;

import core.domain.exercise.Exercise;
import core.domain.workout.WorkoutType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryExerciseRepositoryMock implements IExerciseRepository {
    private final Map<Integer, Exercise> storage = new HashMap<>();

    @Override
    public Optional<Exercise> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Exercise> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public void addExercise(Exercise exercise) {
        storage.put(exercise.id(), exercise);
    }

    @Override
    public Optional<List<Exercise>> findByType(WorkoutType type) {
        List<Exercise> filteredExercises = storage.values().stream()
                                                  .filter(exercise -> exercise.category().matchesWorkoutType(type))
                                                  .collect(Collectors.toList());

        return filteredExercises.isEmpty() ? Optional.empty() : Optional.of(filteredExercises);
    }

    @Override
    public boolean removeExercise(int id) {
        return storage.remove(id) != null;
    }

    public void clear() {
        storage.clear();
    }

    public int count() {
        return storage.size();
    }
}
