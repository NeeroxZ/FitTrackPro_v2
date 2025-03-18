package de.neeroxz.app;

import de.neeroxz.adapters.cli.InputReader;
import de.neeroxz.core.ports.services.IUserService;
import de.neeroxz.core.ports.session.IUserSessionService;
import de.neeroxz.core.usecase.exercise.ExerciseService;
import de.neeroxz.core.usecase.user.AuthenticationUserUseCase;
import de.neeroxz.adapters.cli.panels.AppPanel;
import de.neeroxz.core.usecase.workout.WorkoutUseCaseFactory;

public class App {
    public static void main(String[] args) {
        ServiceFactory factory = new ServiceFactory();

        AuthenticationUserUseCase authService = factory.createAuthService();
        WorkoutUseCaseFactory workoutUseCases = factory.getWorkoutUseCaseFactory();
        InputReader inputReader = factory.createInputReader();
        ExerciseService exerciseService = factory.createExerciseService();
        IUserSessionService userSessionService = factory.getUserSessionService();
        new AppPanel(
                authService,
                workoutUseCases,
                exerciseService,
                authService.getUserRepo(),
                inputReader,
                userSessionService
        ).showPanel();
    }
}
