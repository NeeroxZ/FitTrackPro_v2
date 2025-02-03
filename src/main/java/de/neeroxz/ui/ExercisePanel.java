package de.neeroxz.ui;

import de.neeroxz.exercise.Exercise;
import de.neeroxz.exercise.Workout;
import de.neeroxz.exercise.WorkoutService;
import de.neeroxz.exercise.WorkoutType;
import de.neeroxz.util.AppStrings;

import java.util.List;
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
        Scanner scanner = new Scanner(System.in);
       List<Workout> workouts = workoutService.getUserWorkouts();
        if (workouts.isEmpty()) {
            System.out.println("🔹 Du hast noch keine gespeicherten Workouts.");
            return;
        }

        System.out.println("\n📋 Deine gespeicherten Workouts:");
        for (int i = 0; i < workouts.size(); i++) {
            Workout workout = workouts.get(i);
            System.out.println((i + 1) + ". " + workout.name() + " (" + workout.type() + ")");
        }

        System.out.print("\nWähle ein Workout (Nummer eingeben) oder 0 für Abbruch: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Zeilenumbruch entfernen

        if (choice < 1 || choice > workouts.size()) {
            System.out.println("Ungültige Eingabe oder Abbruch.");
            return;
        }

        Workout selectedWorkout = workouts.get(choice - 1);
        displayWorkoutDetails(selectedWorkout);

    }
    private void displayWorkoutDetails(Workout workout) {
        System.out.println("\n🆔 Workout-ID: " + workout.id());
        System.out.println("📌 Name: " + workout.name());
        System.out.println("🏋 Typ: " + workout.type());
        System.out.println("📃 Übungen:");
        for (Exercise exercise : workout.exercises()) {
            System.out.println("   - " + exercise.name() + " (" + exercise.category() + ")");
        }
        System.out.println(AppStrings.LINESEPARATOR);
    }


    @Override
    public void showPanel() {
        super.handleInput();
    }
}
