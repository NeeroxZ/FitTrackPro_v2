package de.neeroxz.adapters.cli.panels;

import de.neeroxz.core.domain.exercise.*;
import de.neeroxz.core.usecase.exercise.ExerciseService;
import de.neeroxz.adapters.cli.InputReader;
import de.neeroxz.core.usecase.workout.WorkoutUseCaseFactory;
import de.neeroxz.util.AppStrings;
import de.neeroxz.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

/*
 * Refaktorierte Klasse: ExercisePanel.
 */
public class ExercisePanel extends AbstractConsolePanel
{
    private final WorkoutUseCaseFactory workoutUseCaseFactory;
    private final InputReader inputReader;
    private final ExerciseService exerciseService;

    public ExercisePanel(WorkoutUseCaseFactory workoutUseCaseFactory,
                         ExerciseService exerciseService,
                         InputReader inputReader
                        )
    {
        this.workoutUseCaseFactory = workoutUseCaseFactory;
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
        new ExerciseCreatorCLI(exerciseService).createOwnExercise();
    }

    private void deleteWorkout()
    {
        List<Workout> workouts = workoutUseCaseFactory.getUserWorkoutsUseCase().getUserWorkouts();
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
        workoutUseCaseFactory.removeWorkoutUseCase().removeWorkout(workoutToDelete.id());
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
     * TODO: Stimmt hinten und vonre nicht macht auch viel zuviel.
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

        List<TrainingDay> trainingDays = new ArrayList<>();

        // Nutzer muss für jeden Trainingstag Übungen auswählen
        for (int day = 1; day <= frequency; day++)
        {
            System.out.println("\n📆 Wähle Übungen für Tag " + day + " (" + split + " Split):");

            List<Exercise> filteredExercises = exerciseService.filterExercisesBySplit(split);
            List<Exercise> selectedExercises = selectExercises(filteredExercises);

            if (selectedExercises.isEmpty())
            {
                System.out.println("⚠️ Kein Workout für Tag " + day + " erstellt, da keine Übungen ausgewählt wurden.");
            }
            else
            {
                trainingDays.add(new TrainingDay(name + " - Tag " + day, selectedExercises));
            }
        }

        if (trainingDays.isEmpty())
        {
            System.out.println("❌ Kein Workout erstellt, da keine gültigen Übungen ausgewählt wurden.");
            return;
        }

       //todo String username = LoggedInUser.getCurrentUser().get().username();
        String username = "test";
        int workoutId = IdGenerator.generateUniqueId();

       // Workout workout = new Workout(workoutId, name, type, trainingDays, username, frequency, split);
        //workoutUseCases.createWorkoutUseCase.createWorkout(workoutId, name, type, trainingDays, username, frequency, split);
        //System.out.println("\n✅ Workout '" + workout.name() + "' mit " + trainingDays.size() + " Trainingstagen wurde gespeichert!");
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
        List<Workout> wor = workoutUseCaseFactory.createWorkoutUseCase().createWorkout(name, type, frequency, split);
    }

    private void showWorkoutsPanel()
    {
        List<Workout> workouts = workoutUseCaseFactory.getUserWorkoutsUseCase().getUserWorkouts();
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

    private void displayWorkoutOverview(Workout workout)
    {
        System.out.println("\n🆔 Workout-ID: " + workout.id());
        System.out.println("📌 Name: " + workout.name());
        System.out.println("🏋 Typ: " + workout.type());
        System.out.println("📆 Anzahl Trainingstage: " + workout.trainingDays().size());
        System.out.println("👤 Erstellt von: " + workout.username());
        System.out.println("📅 Split: " + workout.split());
        System.out.println("➡️  Gib die Workout-ID ein, um Details zu sehen.");
        System.out.println(AppStrings.LINESEPARATOR);
    }


    private void displayWorkoutDetails(Workout workout)
    {
        System.out.println("\n🆔 Workout-ID: " + workout.id());
        System.out.println("📌 Name: " + workout.name());
        System.out.println("🏋 Typ: " + workout.type());
        System.out.println("📆 Anzahl Trainingstage: " + workout.trainingDays().size());
        System.out.println("👤 Erstellt von: " + workout.username());
        System.out.println("📅 Split: " + workout.split());
        System.out.println("\n📃 Trainingstage:");

        for (TrainingDay trainingDay : workout.trainingDays())
        {
            System.out.println("   📅 " + trainingDay.name());
            for (Exercise exercise : trainingDay.exercises())
            {
                System.out.println("      - " + exercise.name() + " (" + exercise.category() + ")");
            }
            System.out.println();
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
