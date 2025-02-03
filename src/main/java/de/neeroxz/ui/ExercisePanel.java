package de.neeroxz.ui;

import de.neeroxz.exercise.Workout;
import de.neeroxz.exercise.WorkoutService;
import de.neeroxz.exercise.WorkoutType;

import java.util.Scanner;

/**
 * Class: ExercisePanel
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public class ExercisePanel extends AbstractConsolePanel{
    WorkoutService workoutService;

    public ExercisePanel(WorkoutService workoutService) {
        this.workoutService = workoutService;
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
        Scanner scanner = new Scanner(System.in);
        System.out.print("Gib dem Workout einen Namen: ");
        String name = scanner.nextLine();

        System.out.println("Wähle Workout-Typ:");
        System.out.println("1. Kraftsport");
        System.out.println("2. Cardio");
        System.out.println("3. Yoga");
        System.out.print("Deine Wahl: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Zeilenumbruch entfernen

        WorkoutType type;
        switch (choice) {
            case 1 -> type = WorkoutType.KRAFTSPORT;
            case 2 -> type = WorkoutType.CARDIO;
            case 3 -> type = WorkoutType.YOGA;
            default -> {
                System.out.println("Ungültige Eingabe! Abbruch.");
                return;
            }
        }
        Workout workout = workoutService.createRandomWorkout(name, type);
        System.out.println("Workout '" + workout.name() + "' wurde gespeichert!");
    }


    private void showWorkouts() {
        System.out.println("Zurzeit noch nicht Verfügbar");
    }

    @Override
    public void showPanel() {
        super.handleInput();
    }
}
