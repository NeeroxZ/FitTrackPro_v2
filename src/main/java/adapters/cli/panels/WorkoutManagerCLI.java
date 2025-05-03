package adapters.cli.panels;

import adapters.cli.IInputReader;
import core.domain.exercise.TrainingSplit;
import core.domain.workout.Workout;
import core.domain.exercise.Exercise;
import core.domain.workout.WorkoutType;
import core.usecase.workout.WorkoutUseCaseFactory;
import util.AppStrings;

import java.util.List;

public class WorkoutManagerCLI
{
    private final WorkoutUseCaseFactory workoutUseCaseFactory;
    private final IInputReader inputReader;

    public WorkoutManagerCLI(WorkoutUseCaseFactory workoutUseCaseFactory, IInputReader inputReader)
    {
        this.workoutUseCaseFactory = workoutUseCaseFactory;
        this.inputReader = inputReader;
    }

    public void showWorkouts()
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

    public void deleteWorkout()
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

    public List<Workout> generateRandomWorkout(String name, WorkoutType type, TrainingSplit split, int frequency)
    {
        return workoutUseCaseFactory.generateRandomWorkoutUseCase().generate(name, type, split, frequency);
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

        for (var trainingDay : workout.trainingDays())
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
}
