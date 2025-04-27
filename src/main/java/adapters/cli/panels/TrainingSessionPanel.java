package adapters.cli.panels;

import adapters.cli.IInputReader;
import core.domain.workout.Workout;
import core.domain.exercise.Exercise;
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

        System.out.println("\nWähle deinen Trainingsmodus:");
        System.out.println("1: Leicht");
        System.out.println("2: Intensiv");
        System.out.println("3: Challenge");

        int mode = inputReader.readInt("Modus (1/2/3): ");

        Workout selectedWorkout = workouts.get(choice - 1);
        runTrainingSession(selectedWorkout, mode);
    }

    private void runTrainingSession(Workout workout, int mode)
    {
        System.out.println("\n🏋️ Training gestartet: " + workout.name());
        LocalDateTime start = LocalDateTime.now();
        int completedExercises = 0;

        switch (mode)
        {
            case 1: // Leicht
                for (var trainingDay : workout.trainingDays())
                {
                    System.out.println("\n📅 " + trainingDay.name());
                    List<Exercise> exercises = trainingDay.exercises();
                    for (int i = 0; i < Math.min(3, exercises.size()); i++) // Nur 3 Übungen
                    {
                        Exercise exercise = exercises.get(i);
                        System.out.println("➡️ Übung: " + exercise.name() + " (" + exercise.category() + ")");
                        String confirm = inputReader.readLine("Fertig? (j/n): ");
                        if (confirm.equalsIgnoreCase("j"))
                        {
                            completedExercises++;
                        }
                    }
                }
                break;

            case 2: // Intensiv
                for (var trainingDay : workout.trainingDays())
                {
                    System.out.println("\n📅 " + trainingDay.name());
                    for (Exercise exercise : trainingDay.exercises())
                    {
                        System.out.println("➡️ Übung: " + exercise.name() + " (" + exercise.category() + ")");
                        String confirm = inputReader.readLine("Fertig? (j/n): ");
                        if (confirm.equalsIgnoreCase("j"))
                        {
                            completedExercises++;
                        }
                        System.out.println("⏱️ Mach sofort weiter! Keine Pause erlaubt!");
                    }
                }
                break;

            case 3: // Challenge
                for (var trainingDay : workout.trainingDays())
                {
                    System.out.println("\n📅 " + trainingDay.name());
                    for (Exercise exercise : trainingDay.exercises())
                    {
                        System.out.println("➡️ Übung: " + exercise.name() + " (" + exercise.category() + ")");
                        String confirm = inputReader.readLine("Fertig? (j/n): ");
                        if (confirm.equalsIgnoreCase("j"))
                        {
                            completedExercises++;
                        }
                        System.out.println("🔥 Zusatzübung: 20 Burpees! Sofort machen!");
                    }
                }
                break;

            default:
                System.out.println("❌ Ungültiger Modus. Abbruch.");
                return;
        }

        LocalDateTime end = LocalDateTime.now();
        System.out.println("\n✅ Training abgeschlossen!");
        System.out.println("Übungen erledigt: " + completedExercises);
        System.out.println("Gestartet um: " + start);
        System.out.println("Beendet um: " + end);
    }

    @Override
    public void showPanel() throws InterruptedException
    {

    }
}
