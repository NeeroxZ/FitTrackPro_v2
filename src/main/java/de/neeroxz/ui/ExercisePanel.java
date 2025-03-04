package de.neeroxz.ui;

import de.neeroxz.exercise.*;
import de.neeroxz.input.InputReader;
import de.neeroxz.user.LoggedInUser;
import de.neeroxz.util.AppStrings;

import java.util.ArrayList;
import java.util.List;

/**
 * Refaktorierte Klasse: ExercisePanel.
 */
public class ExercisePanel extends AbstractConsolePanel {
    private final WorkoutService workoutService;
    private final InputReader inputReader;
    private final ExerciseService exerciseService;

    public ExercisePanel(WorkoutService workoutService,
                         ExerciseService exerciseService,
                         InputReader inputReader
    ) {
        this.workoutService = workoutService;
        this.exerciseService = exerciseService;
        this.inputReader = inputReader;

        super.addMenuAction("Workouts anzeigen", this::showWorkoutsPanel);
        super.addMenuAction("Workout erstellen", this::createWorkoutPanel);
        super.addMenuAction("Workout löschen", this::deleteWorkout);
        super.addMenuAction("individuell übung erstellen", this::createOwnExercise);
    }

    private void createOwnExercise()
    {
        System.out.println("Zurzeit noch nicht Verfügbar");
    }

    private void deleteWorkout()
    {
        List<Workout> workouts = workoutService.getUserWorkouts();
        if (workouts.isEmpty())
        {
            System.out.println("🔹 Du hast noch keine gespeicherten Workouts.");
            return;
        }
        System.out.println("\n📋 Deine gespeicherten Workouts:");
        for (int i = 0; i < workouts.size(); i++)
        {
            Workout workout = workouts.get(i);
            System.out.println((i + 1) + ". " + workout.name() + " (" + workout.type() + ")");
        }
        int choice = inputReader.readInt("\nWähle ein Workout zum Löschen (Nummer eingeben) "
                + "oder 0 für Abbruch: ");
        if (choice < 1 || choice > workouts.size()) {
            System.out.println("❌ Ungültige Eingabe oder Abbruch.");
            return;
        }
        Workout workoutToDelete = workouts.get(choice - 1);
        workoutService.removeWorkout(workoutToDelete.id());
        System.out.println("🗑️ Workout '" + workoutToDelete.name() + "' wurde gelöscht.");
    }

    private void createWorkoutPanel()
    {
        System.out.println("1: Random");
        System.out.println("2: Individuell");
        int typ = inputReader.readInt("Wähle den Workout-Typ: ");
        switch (typ)
        {
            case 1 -> randomWorkoutPanel();
            case 2 -> individuellWorkoutPanel();
            default -> System.out.println("❌ Ungültige Eingabe! Abbruch.");
        }
    }

    private void individuellWorkoutPanel() {
        String name = inputReader.readLine("Gib dem Workout einen Namen: ");
        WorkoutType type = readWorkoutType();
        if (type == null) {
            System.out.println("❌ Ungültige Eingabe! Abbruch.");
            return;
        }
        List<Exercise> allExercises = workoutService.getAllExercises();
        List<Exercise> selectedExercises = selectExercises(allExercises);
        if (selectedExercises.isEmpty())
        {
            System.out.println("❌ Kein Workout erstellt, da keine gültigen Übungen ausgewählt wurden.");
            return;
        }
        String username = LoggedInUser.getCurrentUser().get().username(); // Eventuell könnte auch der aktuelle User injiziert werden.
        Workout workout = new Workout(0, name, type, selectedExercises, username);
        workoutService.saveWorkout(workout);
        System.out.println("\n✅ Workout '" + workout.name()
                + "' mit " + selectedExercises.size()
                + " Übungen wurde gespeichert!");
    }

    private void randomWorkoutPanel()
    {
        String name = inputReader.readLine("Gib dem Workout einen Namen: ");
        WorkoutType type = readWorkoutType();
        if (type == null)
        {
            System.out.println("❌ Ungültige Eingabe! Abbruch.");
            return;
        }
        Workout workout = workoutService.createRandomWorkout(name, type);
        System.out.println("Workout '" + workout.name() + "' wurde gespeichert!");
    }

    private void showWorkoutsPanel()
    {
        List<Workout> workouts = workoutService.getUserWorkouts();
        if (workouts.isEmpty())
        {
            System.out.println("🔹 Du hast noch keine gespeicherten Workouts.");
            return;
        }
        System.out.println("\n📋 Deine gespeicherten Workouts:");
        for (int i = 0; i < workouts.size(); i++)
        {
            Workout workout = workouts.get(i);
            System.out.println((i + 1) + ". " + workout.name() + " (" + workout.type() + ")");
        }
        int choice = inputReader.readInt(
                "\nWähle ein Workout (Nummer eingeben) oder 0 für Abbruch: ");
        if (choice < 1 || choice > workouts.size())
        {
            System.out.println("❌ Ungültige Eingabe oder Abbruch.");
            return;
        }
        Workout selectedWorkout = workouts.get(choice - 1);
        displayWorkoutDetails(selectedWorkout);
    }

    private void displayWorkoutDetails(Workout workout)
    {
        System.out.println("\n🆔 Workout-ID: " + workout.id());
        System.out.println("📌 Name: " + workout.name());
        System.out.println("🏋 Typ: " + workout.type());
        System.out.println("📃 Übungen:");
        for (Exercise exercise : workout.exercises())
        {
            System.out.println("   - " + exercise.name() + " (" + exercise.category() + ")");
        }
        System.out.println(AppStrings.LINESEPARATOR);
    }


    /**
     * Zeigt die Workout-Typen an und liefert anhand der Benutzereingabe einen gültigen Typ.
     * Liefert null bei ungültiger Auswahl.
     */
    private WorkoutType readWorkoutType()
    {
        System.out.println("Wähle Workout-Typ:");
        System.out.println("1. Kraftsport");
        System.out.println("2. Cardio");
        System.out.println("3. Yoga");
        int choice = inputReader.readInt("Deine Wahl: ");
        return switch (choice)
        {
            case 1 -> WorkoutType.KRAFTSPORT;
            case 2 -> WorkoutType.CARDIO;
            case 3 -> WorkoutType.YOGA;
            default -> null;
        };
    }

    /**
     * Zeigt alle verfügbaren Übungen an und erlaubt die Mehrfachauswahl per Eingabe.
     * Die Auswahl erfolgt als Zahlen (durch Leerzeichen getrennt).
     */
    private List<Exercise> selectExercises(List<Exercise> allExercises)
    {
        List<Exercise> selectedExercises = new ArrayList<>();
        System.out.println("📋 Wähle deine Übungen " +
                "(Nummern eingeben, getrennt durch Leerzeichen, z. B. '1 3 5'):");
        for (int i = 0; i < allExercises.size(); i++) {
            Exercise exercise = allExercises.get(i);
            System.out.println((i + 1) + ". " + exercise.name() + " (" + exercise.category() + ")");
        }
        String input = inputReader.readLine("\nDeine Auswahl: ");
        String[] tokens = input.split("\\s+");
        for (String token : tokens) {
            try {
                int index = Integer.parseInt(token) - 1;
                if (index >= 0 && index < allExercises.size()) {
                    selectedExercises.add(allExercises.get(index));
                } else {
                    System.out.println("⚠ Nummer " + token + " ist ungültig.");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠ '" + token + "' ist keine gültige Nummer.");
            }
        }
        return selectedExercises;
    }

    @Override
    public void showPanel() {
        super.handleInput();
    }
}
