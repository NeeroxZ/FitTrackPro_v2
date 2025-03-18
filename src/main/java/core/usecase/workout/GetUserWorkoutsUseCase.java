package core.usecase.workout;

import core.domain.exercise.Workout;
import core.ports.repository.IWorkoutRepository;
import core.ports.session.IUserSessionService;

import java.util.List;

public class GetUserWorkoutsUseCase {

    private final IWorkoutRepository workoutRepository;
    private final IUserSessionService userSessionService;

    public GetUserWorkoutsUseCase(IWorkoutRepository workoutRepository,
                                  IUserSessionService userSessionService) {
        this.workoutRepository = workoutRepository;
        this.userSessionService = userSessionService;
    }

    public List<Workout> getUserWorkouts() {
        String username = userSessionService.getLoggedInUsername()
                                            .orElseThrow(() -> new IllegalStateException("Kein Benutzer eingeloggt!"));

        return workoutRepository.findByUser(username);
    }
}
