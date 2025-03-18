package de.neeroxz;

import de.neeroxz.exercise.service.WorkoutService;
import de.neeroxz.input.InputReader;
import de.neeroxz.services.AuthenticationService;
import de.neeroxz.services.ServiceFactory;
import de.neeroxz.ui.console.AppPanel;

public class App {
    public static void main(String[] args) {
        ServiceFactory factory = new ServiceFactory();

        AuthenticationService authService = factory.createAuthService();
        WorkoutService workoutService = factory.createWorkoutService();
        InputReader inputReader = factory.createInputReader();

        new AppPanel(
                authService,
                workoutService,
                workoutService.getExerciseService(),
                authService.getUserRepo(),
                inputReader
        ).showPanel();
    }
}
