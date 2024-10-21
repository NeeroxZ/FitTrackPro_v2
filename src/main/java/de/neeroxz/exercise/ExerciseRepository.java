package de.neeroxz.exercise;

import java.util.List;
import java.util.Optional;
/**
 * Interface: ExerciseRepository
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public interface ExerciseRepository {
    Optional<Exercise> findById(int id);
    List<Exercise> findAll();
}
