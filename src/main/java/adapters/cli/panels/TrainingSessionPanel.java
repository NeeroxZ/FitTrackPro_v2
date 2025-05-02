package adapters.cli.panels;

import adapters.cli.IInputReader;
import core.domain.exercise.Exercise;
import core.domain.workout.TrainingMode;
import core.domain.workout.Workout;
import core.usecase.workout.WorkoutUseCaseFactory;

import java.time.LocalDateTime;
import java.util.List;

public class TrainingSessionPanel extends AbstractConsolePanel
{
    private final IInputReader inputReader;
    private final WorkoutUseCaseFactory workoutUseCaseFactory;

    public TrainingSessionPanel(IInputReader inputReader, WorkoutUseCaseFactory workoutUseCaseFactory)
    {
        this.inputReader = inputReader;
        this.workoutUseCaseFactory = workoutUseCaseFactory;

        super.addMenuAction("Training starten", this::startTrainingSession);
    }

    private void startTrainingSession()
    {
        List<Workout> workouts = workoutUseCaseFactory.getUserWorkoutsUseCase().getUserWorkouts();

        if (workouts.isEmpty())
        {
            System.out.println("🔹 Keine Workouts vorhanden!");
            return;
        }

        System.out.println("\n📋 Verfügbare Workouts:");
        for (int i = 0; i < workouts.size(); i++)
        {
            System.out.println((i + 1) + ". " + workouts.get(i).name());
        }

        int choice = inputReader.readInt("\nWähle ein Workout zum Starten (Nummer): ");
        if (choice < 1 || choice > workouts.size())
        {
            System.out.println("❌ Ungültige Eingabe.");
            return;
        }

        Workout selectedWorkout = workouts.get(choice - 1);
        TrainingMode mode = readTrainingMode();
        runTrainingSession(selectedWorkout, mode);
    }

    private TrainingMode readTrainingMode()
    {
        System.out.println("\nWähle deinen Trainingsmodus:");
        System.out.println("1: Leicht");
        System.out.println("2: Intensiv");
        System.out.println("3: Challenge");

        int choice = inputReader.readInt("Modus (1/2/3): ");
        return switch (choice)
        {
            case 1 -> TrainingMode.LIGHT;
            case 2 -> TrainingMode.INTENSE;
            case 3 -> TrainingMode.CHALLENGE;
            default -> {
                System.out.println("❌ Ungültiger Modus. Standardmäßig 'Leicht' gewählt.");
                yield TrainingMode.LIGHT;
            }
        };
    }

    private void runTrainingSession(Workout workout, TrainingMode mode)
    {
        System.out.println("\n🏋️ Training gestartet: " + workout.name());
        LocalDateTime start = LocalDateTime.now();

        int completed = switch (mode)
        {
            case LIGHT -> runLightMode(workout);
            case INTENSE -> runIntenseMode(workout);
            case CHALLENGE -> runChallengeMode(workout);
        };

        LocalDateTime end = LocalDateTime.now();
        System.out.println("\n✅ Training abgeschlossen!");
        System.out.println("Übungen erledigt: " + completed);
        System.out.println("Gestartet um: " + start);
        System.out.println("Beendet um: " + end);
    }

    private int runLightMode(Workout workout)
    {
        int completed = 0;
        for (var day : workout.trainingDays())
        {
            System.out.println("\n📅 " + day.name());
            List<Exercise> exercises = day.exercises();
            for (int i = 0; i < Math.min(3, exercises.size()); i++)
            {
                Exercise exercise = exercises.get(i);
                System.out.println("➡️ Übung: " + exercise.name() + " (" + exercise.category() + ")");
                String input = inputReader.readLine("Fertig? (j/n): ");
                if (input.equalsIgnoreCase("j")) completed++;
            }
        }
        return completed;
    }

    private int runIntenseMode(Workout workout)
    {
        int completed = 0;
        for (var day : workout.trainingDays())
        {
            System.out.println("\n📅 " + day.name());
            for (Exercise exercise : day.exercises())
            {
                System.out.println("➡️ Übung: " + exercise.name() + " (" + exercise.category() + ")");
                String input = inputReader.readLine("Fertig? (j/n): ");
                if (input.equalsIgnoreCase("j")) completed++;
                System.out.println("⏱️ Mach sofort weiter! Keine Pause erlaubt!");
            }
        }
        return completed;
    }

    private int runChallengeMode(Workout workout)
    {
        int completed = 0;
        for (var day : workout.trainingDays())
        {
            System.out.println("\n📅 " + day.name());
            for (Exercise exercise : day.exercises())
            {
                System.out.println("➡️ Übung: " + exercise.name() + " (" + exercise.category() + ")");
                String input = inputReader.readLine("Fertig? (j/n): ");
                if (input.equalsIgnoreCase("j")) completed++;
                System.out.println("🔥 Zusatzübung: 20 Burpees!");
            }
        }
        return completed;
    }

    @Override
    public void showPanel()
    {
        if(workoutUseCaseFactory.getUserWorkoutsUseCase().getUserWorkouts().isEmpty()){
            System.out.println("Keine Workouts vorhanden \n Zurück zum Hauptmenue" );
            return;
        }
        super.handleInput();
    }
}
