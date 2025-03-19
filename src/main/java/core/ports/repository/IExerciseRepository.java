package core.ports.repository;

import core.domain.exercise.Exercise;
import core.domain.workout.WorkoutType;

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

    Optional<List<Exercise>> findByType(WorkoutType type);
}
