package de.neeroxz.exercise;

import java.util.List;
import java.util.Optional;

/**
 * Interface: WorkoutRepository
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */
public interface WorkoutRepository {
    void saveWorkout(Workout workout);
    List<Workout> findAll();
    Optional<Workout> findById(int id);
    List<Workout> findByUser(String username);

}
