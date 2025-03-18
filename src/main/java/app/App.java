package app;

import adapters.cli.InputReader;
import core.ports.session.IUserSessionService;
import core.usecase.exercise.ExerciseService;
import core.usecase.user.AuthenticationUserUseCase;
import adapters.cli.panels.AppPanel;
import core.usecase.user.UserUseCaseFactory;
import core.usecase.workout.WorkoutUseCaseFactory;

public class App {
    public static void main(String[] args) {
        ServiceFactory factory = new ServiceFactory();

        WorkoutUseCaseFactory workoutUseCases = factory.getWorkoutUseCaseFactory();
        UserUseCaseFactory userUseCaseFactory = factory.getUserUseCaseFactory();
        ExerciseService exerciseService = factory.createExerciseService();

        InputReader inputReader = factory.createInputReader();
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
