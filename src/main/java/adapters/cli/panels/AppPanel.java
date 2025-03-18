package adapters.cli.panels;

import core.ports.session.IUserSessionService;
import core.usecase.exercise.ExerciseService;
import adapters.cli.InputReader;
import core.usecase.user.AuthenticationUserUseCase;
import core.ports.services.IUserService;
import core.usecase.user.UserUseCaseFactory;
import core.usecase.workout.WorkoutUseCaseFactory;

/**
 * Class: AppPanel
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class AppPanel extends AbstractConsolePanel
{

    AuthenticationUserUseCase authService;
    WorkoutUseCaseFactory workoutUseCaseFactory;
    ExerciseService exerciseService;
    UserUseCaseFactory userUseCaseFactory;
    InputReader inputReader;
    IUserSessionService userSessionService;

    public AppPanel(AuthenticationUserUseCase authService,
                    WorkoutUseCaseFactory workoutUseCases,
                    ExerciseService exerciseService,
                    UserUseCaseFactory userUseCaseFactory,
                    InputReader inputReader,
                    IUserSessionService userSessionService)
    {
        this.authService = authService;
        this.workoutUseCaseFactory = workoutUseCaseFactory;
        this.userSessionService = userSessionService;
        this.exerciseService = exerciseService;
        this.userUseCaseFactory = userUseCaseFactory;
        this.inputReader = inputReader;
        // Menüaktionen dynamisch hinzufügen
        removeMainMenu();
        addMenuAction("User Einstellungen", this::userSettings);
        addMenuAction("Training auswählen", this::selectTraining);
        addMenuAction("Beenden", this::exitApp);
    }

    @Override
    public void showPanel()
    {
        new LoginPanel(this.userUseCaseFactory, this.inputReader).showPanel();
        new LoadingAnimation().progressBar(10);
        handleInput();
    }

    private void userSettings()
    {
        new UserPanel(userUseCaseFactory, userSessionService, inputReader).showPanel();
    }

    private void selectTraining()
    {

        new ExercisePanel(workoutUseCaseFactory, exerciseService, inputReader).showPanel();
    }

    @Override
    protected boolean isExitOption(int choice)
    {
        // "Beenden" ist die letzte Option, nicht "1"
        return choice == getMenuActionCount();  // Beenden ist die letzte Option
    }

    private void exitApp()
    {
        System.out.println("Programm wird beendet...");
        System.exit(0);
    }
}


