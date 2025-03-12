package de.neeroxz.ui.console;

import de.neeroxz.exercise.model.Exercise;
import de.neeroxz.exercise.model.TrainingSplit;
import de.neeroxz.exercise.model.Workout;
import de.neeroxz.exercise.model.WorkoutType;
import de.neeroxz.exercise.service.ExerciseService;
import de.neeroxz.exercise.service.WorkoutService;
import de.neeroxz.exercise.service.generator.ExerciseCreator;
import de.neeroxz.input.InputReader;
import de.neeroxz.user.session.LoggedInUser;
import de.neeroxz.util.AppStrings;

import java.util.ArrayList;
import java.util.List;

/*
 * Refaktorierte Klasse: ExercisePanel.
 */
public class ExercisePanel extends AbstractConsolePanel
{
    private final WorkoutService workoutService;
    private final InputReader inputReader;
    private final ExerciseService exerciseService;

    public ExercisePanel(WorkoutService workoutService,
                         ExerciseService exerciseService,
                         InputReader inputReader
                        )
    {
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
        //System.out.println("Zurzeit noch nicht Verfügbar");
        new ExerciseCreator(exerciseService).createOwnExercise();
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
        int choice = inputReader.readInt("\nWähle ein Workout zum Löschen (Nummer eingeben) oder 0 für Abbruch: ");
        if (choice < 1 || choice > workouts.size())
        {
            System.out.println("❌ Ungültige Eingabe oder Abbruch.");
            return;
        }
        Workout workoutToDelete = workouts.get(choice - 1);
        workoutService.removeWorkout(workoutToDelete.id());
        System.out.println("🗑️ Workout '" + workoutToDelete.name() + "' wurde gelöscht.");
    }

    private void createWorkoutPanel()
    {
        System.out.println("1: Random erstellt");
        System.out.println("2: Individuell erstellt");
        int typ = inputReader.readInt("Wähle den Workout-Typ: ");
        switch (typ)
        {
            case 1 -> randomWorkoutPanel();
            case 2 -> individuellWorkoutPanel();
            default -> System.out.println("❌ Ungültige Eingabe! Abbruch.");
        }
    }

    /**
     * Individuell erstelltes Workout: Benutzer wählt Übungen aus.
     */
    private void individuellWorkoutPanel()
    {
        String name = inputReader.readLine("Gib dem Workout einen Namen: ");

        WorkoutType type = readWorkoutType();
        if (type == null)
        {
            System.out.println("❌ Ungültige Eingabe! Abbruch.");
            return;
        }

        // Zusätzliche Eingaben für individuelle Workouts:
        int frequency = inputReader.readInt("Wie oft möchtest du pro Woche trainieren? (1-7): ");
        if (frequency < 1 || frequency > 7)
        {
            System.out.println("❌ Ungültige Frequenz! Abbruch.");
            return;
        }

        TrainingSplit split = readTrainingSplit();
        if (split == null)
        {
            System.out.println("❌ Ungültiger Split! Abbruch.");
            return;
        }

        List<Exercise> allExercises = workoutService.getAllExercises();
        List<Exercise> selectedExercises = selectExercises(allExercises);
        if (selectedExercises.isEmpty())
        {
            System.out.println("❌ Kein Workout erstellt, da keine gültigen Übungen ausgewählt wurden.");
            return;
        }

        String username = LoggedInUser.getCurrentUser().get().username();
        // Erstelle das Workout mit den zusätzlichen Daten: frequency und split
        Workout workout = new Workout(0, name, type, selectedExercises, username, frequency, split);
        workoutService.saveWorkout(workout);

        System.out.println("\n✅ Workout '" + workout.name() + "' mit "
                                   + selectedExercises.size() + " Übungen wurde gespeichert!");
    }


    /**
     * Random Workout: Zusätzlich werden Frequenz (Trainingstage pro Woche)
     * und Trainings-Split abgefragt.
     */
    private void randomWorkoutPanel()
    {
        String name = inputReader.readLine("Gib dem Workout einen Namen: ");
        WorkoutType type = readWorkoutType();
        if (type == null)
        {
            System.out.println("❌ Ungültige Eingabe! Abbruch.");
            return;
        }
        // Neue Frage: Wie oft pro Woche trainieren?
        int frequency = inputReader.readInt("Wie oft möchtest du pro Woche trainieren? (1-7): ");
        if (frequency < 1 || frequency > 7)
        {
            System.out.println("❌ Ungültige Frequenz! Abbruch.");
            return;
        }
        // Neue Frage: Welchen Trainings-Split möchtest du trainieren?
        TrainingSplit split = readTrainingSplit();
        if (split == null)
        {
            System.out.println("❌ Ungültiger Split! Abbruch.");
            return;
        }
        // Übergabe der zusätzlichen Parameter an den WorkoutService
        List<Workout> workout = workoutService.createRandomWorkout(name, type, frequency, split);
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
        int choice = inputReader.readInt("\nWähle ein Workout (Nummer eingeben) oder 0 für Abbruch: ");
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
        System.out.println("📋 Wähle deine Übungen (Nummern eingeben, getrennt durch Leerzeichen, z. B. '1 3 5'):");
        for (int i = 0; i < allExercises.size(); i++)
        {
            Exercise exercise = allExercises.get(i);
            System.out.println((i + 1) + ". " + exercise.name() + " (" + exercise.category() + ")");
        }
        String input = inputReader.readLine("\nDeine Auswahl: ");
        String[] tokens = input.split("\\s+");
        for (String token : tokens)
        {
            try
            {
                int index = Integer.parseInt(token) - 1;
                if (index >= 0 && index < allExercises.size())
                {
                    selectedExercises.add(allExercises.get(index));
                }
                else
                {
                    System.out.println("⚠ Nummer " + token + " ist ungültig.");
                }
            } catch (NumberFormatException e)
            {
                System.out.println("⚠ '" + token + "' ist keine gültige Nummer.");
            }
        }
        return selectedExercises;
    }

    /**
     * Fragt den Trainings-Split ab und gibt das passende Enum zurück.
     * Optionen:
     * 1. Oberkörper/Unterkörper
     * 2. Push_PULL_LEG
     * 3. Ganzkörper
     */
    private TrainingSplit readTrainingSplit()
    {
        System.out.println("Wähle den Trainings-Split:");
        System.out.println("1. Oberkörper/Unterkörper");
        System.out.println("2. Push, Pull, Leg");
        System.out.println("3. Ganzkörper");
        int choice = inputReader.readInt("Deine Wahl: ");
        return switch (choice)
        {
            case 1 -> TrainingSplit.OBER_UNTER;
            case 2 -> TrainingSplit.PUSH_PULL_LEG;
            case 3 -> TrainingSplit.GANZKORPER;
            default -> null;
        };
    }

    @Override
    public void showPanel()
    {
        super.handleInput();
    }
}
