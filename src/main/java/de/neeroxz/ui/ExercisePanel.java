package de.neeroxz.ui;

import de.neeroxz.exercise.Exercise;
import de.neeroxz.exercise.Workout;
import de.neeroxz.exercise.WorkoutService;
import de.neeroxz.exercise.WorkoutType;
import de.neeroxz.user.LoggedInUser;
import de.neeroxz.util.AppStrings;

import java.util.ArrayList;
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
    Scanner scanner = new Scanner(System.in);

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

        System.out.print("\nWähle ein Workout zum Löschen (Nummer eingeben) oder 0 für Abbruch: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Zeilenumbruch entfernen

        if (choice < 1 || choice > workouts.size()) {
            System.out.println("❌ Ungültige Eingabe oder Abbruch.");
            return;
        }

        Workout workoutToDelete = workouts.get(choice - 1);
        workoutService.removeWorkout(workoutToDelete.id());

        System.out.println("🗑️ Workout '" + workoutToDelete.name() + "' wurde gelöscht.");
    }


    private void createWorkout() {
        System.out.println("1: Random");
        System.out.println("2: Individuell");

        int typ = scanner.nextInt();

        switch (typ) {
            case 1 -> randomWorkout();
            case 2 -> individuellWorkout();
            default -> {
                System.out.println("❌ Ungültige Eingabe! Abbruch.");
                return;
            }
        }
    }

    private void individuellWorkout() {
        System.out.print("Gib dem Workout einen Namen: ");
        String name = scanner.nextLine();

        System.out.println("Wähle Workout-Typ:");
        System.out.println("1. Kraftsport");
        System.out.println("2. Cardio");
        System.out.println("3. Yoga");
        System.out.print("Deine Wahl: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        WorkoutType type;
        switch (choice) {
            case 1 -> type = WorkoutType.KRAFTSPORT;
            case 2 -> type = WorkoutType.CARDIO;
            case 3 -> type = WorkoutType.YOGA;
            default -> {
                System.out.println("❌ Ungültige Eingabe! Abbruch.");
                return;
            }
        }

        List<Exercise> allExercises = workoutService.getAllExercises();
        List<Exercise> selectedExercises = new ArrayList<>();

        System.out.println("\n📋 Wähle deine Übungen (Nummern eingeben, getrennt durch Leerzeichen, z. B. '1 3 5'):");
        for (int i = 0; i < allExercises.size(); i++) {
            Exercise exercise = allExercises.get(i);
            System.out.println((i + 1) + ". " + exercise.name() + " (" + exercise.category() + ")");
        }

        System.out.print("\nDeine Auswahl: ");
        String[] input = scanner.nextLine().split(" ");

        for (String number : input) {
            try {
                int exerciseIndex = Integer.parseInt(number) - 1;
                if (exerciseIndex >= 0 && exerciseIndex < allExercises.size()) {
                    selectedExercises.add(allExercises.get(exerciseIndex));
                } else {
                    System.out.println("⚠ Nummer " + number + " ist ungültig.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ '" + number + "' ist keine gültige Nummer.");
            }
        }

        if (selectedExercises.isEmpty()) {
            System.out.println("❌ Kein Workout erstellt, da keine gültigen Übungen ausgewählt wurden.");
            return;
        }

        Workout workout = new Workout(0, name, type, selectedExercises, LoggedInUser.getCurrentUser().get().username());
        workoutService.saveWorkout(workout);

        System.out.println("\n✅ Workout '" + workout.name() + "' mit " + selectedExercises.size() + " Übungen wurde gespeichert!");
    }

    private void randomWorkout(){
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
