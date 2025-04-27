package adapters.cli.panels;

import adapters.cli.IInputReader;
import core.domain.exercise.*;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.usecase.exercise.GetExercisesUseCase;
import core.usecase.workout.WorkoutUseCaseFactory;
import util.IdGenerator;

import java.util.ArrayList;
import java.util.List;

public class ExercisePanel extends AbstractConsolePanel
{
    private final WorkoutManagerCLI workoutManager;
    private final IInputReader inputReader;
    private final GetExercisesUseCase exerciseService;

    public ExercisePanel(WorkoutUseCaseFactory workoutUseCaseFactory,
                         GetExercisesUseCase exerciseService,
                         IInputReader inputReader)
    {
        this.workoutManager = new WorkoutManagerCLI(workoutUseCaseFactory, inputReader);
        this.exerciseService = exerciseService;
        this.inputReader = inputReader;

        super.addMenuAction("Workouts anzeigen", workoutManager::showWorkouts);
        super.addMenuAction("Workout erstellen", this::createWorkoutPanel);
        super.addMenuAction("Workout löschen", workoutManager::deleteWorkout);
        super.addMenuAction("individuell Übung erstellen", this::createOwnExercise);
    }

    protected void createOwnExercise()
    {
        new ExerciseCreatorCLI(exerciseService).createOwnExercise();
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

    private void individuellWorkoutPanel()
    {
        String name = readWorkoutName();
        WorkoutType type = readWorkoutType();
        if (type == null) return;

        int frequency = readFrequency();
        if (frequency == -1) return;

        TrainingSplit split = readTrainingSplit();
        if (split == null) return;

        List<TrainingDay> trainingDays = collectTrainingDays(frequency, split, name);

        if (trainingDays.isEmpty())
        {
            System.out.println("❌ Kein Workout erstellt, da keine gültigen Übungen ausgewählt wurden.");
            return;
        }

        String username = "test"; // TODO: Replace with logged in user
        int workoutId = IdGenerator.generateUniqueId();

        // Hier sollte dein Save-Usecase aufgerufen werden.
        System.out.println("\n✅ Workout '" + name + "' mit " + trainingDays.size() + " Trainingstagen wurde gespeichert!");
    }

    private void randomWorkoutPanel()
    {
        String name = readWorkoutName();
        WorkoutType type = readWorkoutType();
        if (type == null) return;

        int frequency = readFrequency();
        if (frequency == -1) return;

        TrainingSplit split = readTrainingSplit();
        if (split == null) return;

        List<Workout> workouts = workoutManager.generateRandomWorkout(name, type, split, frequency);
    }

    private String readWorkoutName()
    {
        return inputReader.readLine("Gib dem Workout einen Namen: ");
    }

    private int readFrequency()
    {
        int frequency = inputReader.readInt("Wie oft möchtest du pro Woche trainieren? (1-7): ");
        if (frequency < 1 || frequency > 7)
        {
            System.out.println("❌ Ungültige Frequenz! Abbruch.");
            return -1;
        }
        return frequency;
    }

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
            default -> {
                System.out.println("❌ Ungültige Eingabe! Abbruch.");
                yield null;
            }
        };
    }

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

    private List<TrainingDay> collectTrainingDays(int frequency, TrainingSplit split, String workoutName)
    {
        List<TrainingDay> trainingDays = new ArrayList<>();

        for (int day = 1; day <= frequency; day++)
        {
            System.out.println("\n📆 Wähle Übungen für Tag " + day + " (" + split + " Split):");

            List<Exercise> filteredExercises = exerciseService.filterExercisesBySplit(split);
            List<Exercise> selectedExercises = selectExercises(filteredExercises);

            if (!selectedExercises.isEmpty())
            {
                trainingDays.add(new TrainingDay(workoutName + " - Tag " + day, selectedExercises));
            }
            else
            {
                System.out.println("⚠️ Kein Workout für Tag " + day + " erstellt.");
            }
        }
        return trainingDays;
    }

    private List<Exercise> selectExercises(List<Exercise> allExercises)
    {
        List<Exercise> selectedExercises = new ArrayList<>();
        System.out.println("📋 Wähle deine Übungen (Nummern eingeben, getrennt durch Leerzeichen):");

        for (int i = 0; i < allExercises.size(); i++)
        {
            System.out.println((i + 1) + ". " + allExercises.get(i).name() + " (" + allExercises.get(i).category() + ")");
        }

        String input = inputReader.readLine("Deine Auswahl: ");
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
            }
            catch (NumberFormatException e)
            {
                System.out.println("⚠ '" + token + "' ist keine gültige Nummer.");
            }
        }
        return selectedExercises;
    }

    @Override
    public void showPanel()
    {
        super.handleInput();
    }
}
