package core.usecase.workout;

import core.domain.exercise.TrainingSplit;
import core.domain.user.User;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IWorkoutRepository;
import core.ports.session.IUserSessionService;
import core.ports.workout.IWorkoutGenerator;

import java.util.List;

public class CreateWorkoutUseCase
{

    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;
    private final IWorkoutGenerator workoutGenerator;

    public CreateWorkoutUseCase(IWorkoutRepository workoutRepository,
                                IUserSessionService userSessionService,
                                IWorkoutGenerator workoutGenerator)
    {
        this.workoutRepository = workoutRepository;
        this.userSessionService = userSessionService;
        this.workoutGenerator = workoutGenerator;
    }

    public List<Workout> createWorkout(String name, WorkoutType type, int frequency, TrainingSplit split)
    {
        String username = userSessionService.getLoggedInUser().map(User::username)
                                            .orElseThrow(RuntimeException::new);

        List<Workout> workouts = workoutGenerator.generateWorkout(name, type, split, frequency, username);
        workouts.forEach(workoutRepository::saveWorkout);
        return workouts;
    }
}
