package adapters.cli.panels;

import core.ports.session.IUserSessionObserver;
import core.ports.session.IUserSessionService;
import core.usecase.exercise.GetExercisesUseCase;
import adapters.cli.IInputReader;
import core.usecase.user.UserUseCaseFactory;
import core.usecase.workout.WorkoutUseCaseFactory;

/**
 * Class: AppPanel
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class AppPanel extends AbstractConsolePanel implements IUserSessionObserver
{

    WorkoutUseCaseFactory workoutUseCaseFactory;
    GetExercisesUseCase exerciseService;
    UserUseCaseFactory userUseCaseFactory;
    IInputReader inputReader;
    IUserSessionService userSessionService;

    public AppPanel(
                    WorkoutUseCaseFactory workoutUseCaseFactory,
                    GetExercisesUseCase exerciseService,
                    UserUseCaseFactory userUseCaseFactory,
                    IInputReader inputReader,
                    IUserSessionService userSessionService)
    {
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

    @Override
    public void onUserLogout()
    {
        this.showPanel();
    }
}


