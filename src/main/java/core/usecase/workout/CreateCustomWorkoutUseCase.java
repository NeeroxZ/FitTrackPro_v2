package core.usecase.workout;

import core.domain.exercise.TrainingDay;
import core.domain.user.User;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IWorkoutRepository;
import core.ports.session.IUserSessionService;
import core.ports.workout.ICustomWorkoutGenerator;

import java.util.List;

public class CreateCustomWorkoutUseCase {

    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;
    private final ICustomWorkoutGenerator workoutGenerator;

    public CreateCustomWorkoutUseCase(IWorkoutRepository workoutRepository,
                                      IUserSessionService userSessionService,
                                      ICustomWorkoutGenerator workoutGenerator) {
        this.workoutRepository = workoutRepository;
        this.userSessionService = userSessionService;
        this.workoutGenerator = workoutGenerator;
    }

    public Workout create(String name, WorkoutType type, List<TrainingDay> days) {
        String username = userSessionService.getLoggedInUser()
                                            .map(User::username)
                                            .orElseThrow(() -> new IllegalStateException("Kein Nutzer eingeloggt"));

        Workout workout = workoutGenerator.create(name, type, days, username);
        workoutRepository.saveWorkout(workout);
        return workout;
    }
}
