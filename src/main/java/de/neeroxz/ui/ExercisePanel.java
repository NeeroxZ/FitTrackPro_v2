package de.neeroxz.ui;

/**
 * Class: ExercisePanel
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public class ExercisePanel extends AbstractConsolePanel{

    public ExercisePanel() {
        super.addMenuAction("Workouts anzeigen", this::showWorkouts);
        super.addMenuAction("Workout erstellen", this::createWorkout);
        super.addMenuAction("Workout löschen", this::deleteWorkout);
        super.addMenuAction("individuell übung erstellen", this::createOwnExercise);
    }

    private void createOwnExercise() {
        System.out.println("Zurzeit noch nicht Verfügbar");
    }

    private void deleteWorkout() {
        System.out.println("Zurzeit noch nicht Verfügbar");
    }

    private void createWorkout() {
        System.out.println("Zurzeit noch nicht Verfügbar");
    }

    private void showWorkouts() {
        System.out.println("Zurzeit noch nicht Verfügbar");
    }

    @Override
    public void showPanel() {
        super.handleInput();
    }
}
