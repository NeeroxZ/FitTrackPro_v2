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
public interface IExerciseRepository
{
    Optional<Exercise> findById(int id);

    List<Exercise> findAll();

    void addExercise(Exercise exercise);
}
