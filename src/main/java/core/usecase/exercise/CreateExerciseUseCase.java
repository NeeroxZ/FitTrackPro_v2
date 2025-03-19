package core.usecase.exercise;

import core.domain.exercise.Exercise;
import core.ports.repository.IExerciseRepository;

public class CreateExerciseUseCase {
    private final IExerciseRepository exerciseRepository;

    public CreateExerciseUseCase(IExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public void execute(Exercise exercise) {
        exerciseRepository.addExercise(exercise);
    }
}
