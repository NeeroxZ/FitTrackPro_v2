package de.neeroxz.core.usecase.workout;

import de.neeroxz.core.ports.repository.IWorkoutRepository;
import de.neeroxz.core.ports.session.IUserSessionService;
import de.neeroxz.core.ports.workout.IWorkoutGenerator;

public class WorkoutUseCaseFactory {

    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;
    private final IWorkoutGenerator workoutGenerator;

    public WorkoutUseCaseFactory(IWorkoutRepository workoutRepository,
                                 IUserSessionService userSessionService,
                                 IWorkoutGenerator workoutGenerator) {
        this.workoutRepository = workoutRepository;
        this.userSessionService = userSessionService;
        this.workoutGenerator = workoutGenerator;
    }

    public CreateWorkoutUseCase createWorkoutUseCase() {
        return new CreateWorkoutUseCase(workoutRepository, userSessionService, workoutGenerator);
    }

    public GetUserWorkoutsUseCase getUserWorkoutsUseCase() {
        return new GetUserWorkoutsUseCase(workoutRepository, userSessionService);
    }

    public RemoveWorkoutUseCase removeWorkoutUseCase() {
        return new RemoveWorkoutUseCase(workoutRepository);
    }
}

