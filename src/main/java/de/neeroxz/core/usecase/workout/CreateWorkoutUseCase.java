package de.neeroxz.core.usecase.workout;

import de.neeroxz.core.domain.exercise.TrainingSplit;
import de.neeroxz.core.domain.exercise.Workout;
import de.neeroxz.core.domain.exercise.WorkoutType;
import de.neeroxz.core.ports.repository.IWorkoutRepository;
import de.neeroxz.core.ports.session.IUserSessionService;
import de.neeroxz.core.ports.workout.IWorkoutGenerator;

import java.util.List;

public class CreateWorkoutUseCase {

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

    public List<Workout> createWorkout(String name, WorkoutType type, int frequency, TrainingSplit split) {
        String username = userSessionService.getLoggedInUsername()
                                            .orElse(null);

        if (username == null) {
            throw new IllegalStateException("Kein Benutzer eingeloggt!");
        }

        List<Workout> workouts = workoutGenerator.generateWorkout(name, type, split, frequency, username);
        workouts.forEach(workoutRepository::saveWorkout);
        return workouts;
    }
}
