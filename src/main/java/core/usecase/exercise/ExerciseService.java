package core.usecase.exercise;

import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.TrainingSplit;
import core.domain.exercise.WorkoutType;
import core.ports.repository.IExerciseRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Class: ExerciseService
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public class ExerciseService
{
    private final IExerciseRepository exerciseRepository;

    public ExerciseService(IExerciseRepository exerciseRepository)
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
        exerciseRepository.addExercise(exercise);
    }

    public List<Exercise> getExercisesByType(WorkoutType type)
    {
        return exerciseRepository
                .findAll()
                .stream()
                .filter(e -> e.category().getWorkoutType() == type)
                .collect(Collectors.toList());
    }

    public List<Exercise> filterExercisesBySplit(TrainingSplit split)
    {
        List<Exercise> exercises = exerciseRepository.findAll(); // Übungen direkt aus dem Repository holen

        return switch (split)
        {
            case GANZKORPER -> exercises;
            case OBER_UNTER -> exercises.stream()
                                        .filter(e -> Set.of(
                                                ExerciseCategory.BRUST,
                                                ExerciseCategory.RUECKEN,
                                                ExerciseCategory.SEITLICHE_SCHULTER,
                                                ExerciseCategory.VORDERE_SCHULTER,
                                                ExerciseCategory.HINTERE_SCHULTER,
                                                ExerciseCategory.BIZEPS,
                                                ExerciseCategory.TRIZEPS,
                                                ExerciseCategory.BEINE,
                                                ExerciseCategory.CORE
                                                           ).contains(e.category()))
                                        .toList();

            case PUSH_PULL_LEG -> exercises.stream()
                                           .filter(e -> Set.of(
                                                   ExerciseCategory.BRUST,
                                                   ExerciseCategory.HINTERE_SCHULTER,
                                                   ExerciseCategory.TRIZEPS,
                                                   ExerciseCategory.RUECKEN,
                                                   ExerciseCategory.BIZEPS,
                                                   ExerciseCategory.BEINE,
                                                   ExerciseCategory.CORE
                                                              ).contains(e.category()))
                                           .toList();

            case PUSH -> exercises.stream()
                                  .filter(e -> Set.of(
                                          ExerciseCategory.BRUST,
                                          ExerciseCategory.HINTERE_SCHULTER,
                                          ExerciseCategory.TRIZEPS
                                                     ).contains(e.category()))
                                  .toList();

            case PULL -> exercises.stream()
                                  .filter(e -> Set.of(
                                          ExerciseCategory.RUECKEN,
                                          ExerciseCategory.BIZEPS
                                                     ).contains(e.category()))
                                  .toList();

            case LEG -> exercises.stream()
                                 .filter(e -> Set.of(
                                         ExerciseCategory.BEINE,
                                         ExerciseCategory.CORE
                                                    ).contains(e.category()))
                                 .toList();
        };
    }
}
