package core.usecase.workout;

import core.domain.exercise.TrainingSplit;
import core.domain.user.User;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IWorkoutRepository;
import core.ports.session.IUserSessionService;
import core.ports.workout.IRandomWorkoutGenerator;

import java.util.List;

public class GenerateRandomWorkoutUseCase {

    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;
    private final IRandomWorkoutGenerator workoutGenerator;

    public GenerateRandomWorkoutUseCase(IWorkoutRepository workoutRepository,
                                        IUserSessionService userSessionService,
                                        IRandomWorkoutGenerator workoutGenerator) {
        this.workoutRepository = workoutRepository;
        this.userSessionService = userSessionService;
        this.workoutGenerator = workoutGenerator;
    }

    public List<Workout> generate(String name, WorkoutType type, TrainingSplit split, int frequency) {
        String username = userSessionService.getLoggedInUser()
                                            .map(User::username)
                                            .orElseThrow(() -> new IllegalStateException("Kein Nutzer eingeloggt"));

        List<Workout> workouts = workoutGenerator.generate(name, type, split, frequency, username);
        workouts.forEach(workoutRepository::saveWorkout);
        return workouts;
    }
}
