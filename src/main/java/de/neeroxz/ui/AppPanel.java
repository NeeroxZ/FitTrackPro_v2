package de.neeroxz.ui;

import de.neeroxz.exercise.ExerciseService;
import de.neeroxz.exercise.WorkoutService;
import de.neeroxz.services.AuthenticationService;

/**
 * Class: AppPanel
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */

public class AppPanel extends AbstractConsolePanel {

    AuthenticationService authService;
    WorkoutService workoutService;
    ExerciseService exerciseService;
    public AppPanel(AuthenticationService authService, WorkoutService workoutService, ExerciseService exerciseService) {
        this.authService = authService;
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;

        // Menüaktionen dynamisch hinzufügen
        removeMainMenu();
        addMenuAction("User Einstellungen", this::userSettings);
        addMenuAction("Training auswählen", this::selectTraining);
        addMenuAction("Beenden", this::exitApp);
    }

    @Override
    public void showPanel() {
        new LoginPanel(this.authService).showPanel();
        new LoadingAnimation().progressBar(10);
        handleInput();
    }

    private void userSettings() {
        new UserPanel().showPanel();
    }

    private void selectTraining()
    {
        new ExercisePanel(workoutService, exerciseService).showPanel();
    }

    @Override
    protected boolean isExitOption(int choice) {
        // "Beenden" ist die letzte Option, nicht "1"
        return choice == getMenuActionCount();  // Beenden ist die letzte Option
    }

    private void exitApp() {
        System.out.println("Programm wird beendet...");
        System.exit(0);
    }
}


