package core.ports.repository;

import core.domain.workout.Workout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryWorkoutRepositoryMock implements IWorkoutRepository {
    private final Map<Integer, Workout> storage = new HashMap<>();

    @Override
    public void saveWorkout(Workout workout) {
        storage.put(workout.id(), workout);
    }

    @Override
    public List<Workout> findAll() {
        return List.copyOf(storage.values());
    }

    @Override
    public Optional<Workout> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Workout> findByUser(String username) {
        return storage.values().stream()
                      .filter(w -> w.username().equals(username))
                      .toList();
    }

    @Override
    public boolean deleteById(int id) {
        return storage.remove(id) != null;
    }

    public void clear() {
        storage.clear();
    }

    public int count() {
        return storage.size();
    }
}
