package core.usecase.exercise;

import core.domain.exercise.Exercise;
import core.domain.workout.WorkoutType;
import core.ports.repository.IExerciseRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GetExercisesByTypeUseCase {
    private final IExerciseRepository exerciseRepository;

    public GetExercisesByTypeUseCase(IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> execute(WorkoutType type) {
        return exerciseRepository.findAll()
                                 .stream()
                                 .filter(e -> e.category().getWorkoutType() == type)
                                 .collect(Collectors.toList());
    }
}

