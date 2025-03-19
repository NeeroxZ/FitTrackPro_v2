package core.usecase.exercise;

import core.domain.exercise.Exercise;
import core.ports.repository.IExerciseRepository;

import java.util.Optional;

public class GetExerciseByIdUseCase {
    private final IExerciseRepository exerciseRepository;

    public GetExerciseByIdUseCase(IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Optional<Exercise> execute(int id) {
        return exerciseRepository.findById(id);
    }
}
