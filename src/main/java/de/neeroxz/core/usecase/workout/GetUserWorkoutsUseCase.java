package de.neeroxz.core.usecase.workout;

import de.neeroxz.core.domain.exercise.Workout;
import de.neeroxz.core.ports.repository.IWorkoutRepository;
import de.neeroxz.core.ports.session.IUserSessionService;

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
