package de.neeroxz.exercise.repository;

import de.neeroxz.exercise.model.Exercise;

import java.util.List;
import java.util.Optional;

/**
 * Interface: ExerciseRepository
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public interface ExerciseRepository
{
    Optional<Exercise> findById(int id);

    List<Exercise> findAll();
}
