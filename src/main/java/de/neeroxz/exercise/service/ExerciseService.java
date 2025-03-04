package de.neeroxz.exercise.service;

import de.neeroxz.exercise.model.Exercise;
import de.neeroxz.exercise.model.WorkoutType;
import de.neeroxz.exercise.repository.ExerciseRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class: ExerciseService
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseService(ExerciseRepository exerciseRepository)
    {
        this.exerciseRepository = exerciseRepository;
    }

    public Optional<Exercise> getExerciseById(int id)
    {
        return exerciseRepository.findById(id);
    }

    public List<Exercise> getAllExercises()
    {
        return exerciseRepository.findAll();
    }


    public void createExercise(Exercise exercise)
    {
    //todo
    }
    public List<Exercise> getExercisesByType(WorkoutType type)
    {
        return exerciseRepository
                .findAll()
                .stream()
                .filter(e -> e.category().getWorkoutType() == type)
                .collect(Collectors.toList());
    }
}
