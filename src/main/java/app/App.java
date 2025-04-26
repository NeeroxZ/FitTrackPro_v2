package app;

import adapters.cli.IInputReader;
import core.ports.session.IUserSessionService;
import core.usecase.exercise.GetExercisesUseCase;
import adapters.cli.panels.AppPanel;
import core.usecase.user.UserUseCaseFactory;
import core.usecase.workout.WorkoutUseCaseFactory;

public class App {
    public static void main(String[] args) {
        ServiceFactory factory = new ServiceFactory();

        WorkoutUseCaseFactory workoutUseCases = factory.getWorkoutUseCaseFactory();
        UserUseCaseFactory userUseCaseFactory = factory.getUserUseCaseFactory();
        GetExercisesUseCase exerciseService = factory.createExerciseService();

        IInputReader inputReader = factory.createInputReader();
        IUserSessionService userSessionService = factory.getUserSessionService();
        new AppPanel(
                workoutUseCases,
                exerciseService,
                userUseCaseFactory,
                inputReader,
                userSessionService
        ).showPanel();
    }
}
