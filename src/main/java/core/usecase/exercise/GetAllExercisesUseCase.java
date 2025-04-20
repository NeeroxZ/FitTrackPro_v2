package core.usecase.exercise;

import core.domain.exercise.Exercise;
import core.ports.repository.IExerciseRepository;

import java.util.List;

public class GetAllExercisesUseCase {
    private final IExerciseRepository exerciseRepository;

    public GetAllExercisesUseCase(IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public List<Exercise> getAll() {
        return exerciseRepository.findAll();
    }
}
