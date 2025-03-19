package core.usecase.exercise;

import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.TrainingSplit;
import core.ports.repository.IExerciseRepository;

import java.util.List;
import java.util.Set;

public class FilterExercisesBySplitUseCase {
    private final IExerciseRepository exerciseRepository;

    public FilterExercisesBySplitUseCase(IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> execute(TrainingSplit split) {
        List<Exercise> exercises = exerciseRepository.findAll();

        return switch (split) {
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
