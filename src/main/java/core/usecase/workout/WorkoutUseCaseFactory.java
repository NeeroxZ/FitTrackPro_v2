package core.usecase.workout;

import core.domain.workout.plans.IWorkoutPlan;
import core.ports.repository.IExerciseRepository;
import core.ports.repository.IWorkoutRepository;
import core.ports.session.IUserSessionService;
import core.domain.workout.generator.CustomWorkoutGenerator;
import core.ports.workout.ICustomWorkoutGenerator;
import core.ports.workout.IRandomWorkoutGenerator;
import core.domain.workout.generator.RandomWorkoutGenerator;

import java.util.List;

public class WorkoutUseCaseFactory {

    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;
    private final ICustomWorkoutGenerator customWorkoutGenerator;
    private final IRandomWorkoutGenerator randomWorkoutGenerator;

    public WorkoutUseCaseFactory(IWorkoutRepository workoutRepository,
                                 IExerciseRepository exerciseRepository,
                                 IUserSessionService userSessionService,
                                 List<IWorkoutPlan> plans) {
        this.workoutRepository = workoutRepository;
        this.userSessionService = userSessionService;
        this.randomWorkoutGenerator = new RandomWorkoutGenerator(exerciseRepository, plans);
        this.customWorkoutGenerator = new CustomWorkoutGenerator();
    }

    /*
    public CreateWorkoutUseCase createWorkoutUseCase() {
        return new CreateWorkoutUseCase(workoutRepository, userSessionService, workoutGenerator);
    }
    */
    public GenerateRandomWorkoutUseCase generateRandomWorkoutUseCase(){
        return new GenerateRandomWorkoutUseCase(workoutRepository, userSessionService, randomWorkoutGenerator);
    }
    public CreateCustomWorkoutUseCase createCustomWorkoutUseCase(){
        return new CreateCustomWorkoutUseCase(workoutRepository, userSessionService, customWorkoutGenerator);
    }
    public GetUserWorkoutsUseCase getUserWorkoutsUseCase() {
        return new GetUserWorkoutsUseCase(workoutRepository, userSessionService);
    }

    public RemoveWorkoutUseCase removeWorkoutUseCase() {
        return new RemoveWorkoutUseCase(workoutRepository);
    }
}

