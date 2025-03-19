package core.usecase.exercise;


import core.domain.exercise.Exercise;
import core.domain.exercise.TrainingSplit;
import core.domain.workout.WorkoutType;
import core.ports.repository.IExerciseRepository;

import java.util.List;
import java.util.Optional;

public class GetExercisesUseCase
{
    private final GetExerciseByIdUseCase getExerciseByIdUseCase;
    private final GetAllExercisesUseCase getAllExercisesUseCase;
    private final CreateExerciseUseCase createExerciseUseCase;
    private final GetExercisesByTypeUseCase getExercisesByTypeUseCase;
    private final FilterExercisesBySplitUseCase filterExercisesBySplitUseCase;

    public GetExercisesUseCase(IExerciseRepository exerciseRepository) {
        this.getExerciseByIdUseCase = new GetExerciseByIdUseCase(exerciseRepository);
        this.getAllExercisesUseCase = new GetAllExercisesUseCase(exerciseRepository);
        this.createExerciseUseCase = new CreateExerciseUseCase(exerciseRepository);
        this.getExercisesByTypeUseCase = new GetExercisesByTypeUseCase(exerciseRepository);
        this.filterExercisesBySplitUseCase = new FilterExercisesBySplitUseCase(exerciseRepository);
    }

    public Optional<Exercise> getExerciseById(int id) {
        return getExerciseByIdUseCase.execute(id);
    }

    public List<Exercise> getAllExercises() {
        return getAllExercisesUseCase.execute();
    }

    public void createExercise(Exercise exercise) {
        createExerciseUseCase.execute(exercise);
    }

    public List<Exercise> getExercisesByType(WorkoutType type) {
        return getExercisesByTypeUseCase.execute(type);
    }

    public List<Exercise> filterExercisesBySplit(TrainingSplit split) {
        return filterExercisesBySplitUseCase.execute(split);
    }
}
