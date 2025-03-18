package core.usecase.workout;

import core.ports.repository.IWorkoutRepository;

public class RemoveWorkoutUseCase {

    private final IWorkoutRepository workoutRepository;

    public RemoveWorkoutUseCase(IWorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void removeWorkout(int id) {
        workoutRepository.deleteById(id);
    }
}
