package de.neeroxz.core.usecase.workout;

import de.neeroxz.core.ports.repository.IWorkoutRepository;

public class RemoveWorkoutUseCase {

    private final IWorkoutRepository workoutRepository;

    public RemoveWorkoutUseCase(IWorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public void removeWorkout(int id) {
        workoutRepository.deleteById(id);
    }
}
