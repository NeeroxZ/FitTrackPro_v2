import de.neeroxz.exercise.service.ExerciseService;
import de.neeroxz.exercise.service.WorkoutService;
import de.neeroxz.input.InputReader;
import de.neeroxz.services.AuthenticationService;
import de.neeroxz.ui.console.AppPanel;

/**
 * Class: App
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class App
{
    public static void main(String[] args)
    {

        AuthenticationService authService = AppConfig.createAuthService();
        ExerciseService exerciseService = AppConfig.createExerciseService();
        WorkoutService workoutService = AppConfig.createWorkoutService(exerciseService);
        InputReader inputReader = AppConfig.createInputReader();

        new AppPanel(authService,
                     workoutService,
                     exerciseService,
                     authService.getUserRepo(),
                     inputReader).showPanel();
    }
}
