package de.neeroxz.exercise.service.generator;

import de.neeroxz.exercise.model.Difficulty;
import de.neeroxz.exercise.model.Exercise;
import de.neeroxz.exercise.model.ExerciseCategory;
import de.neeroxz.exercise.model.MuscleGroup;
import de.neeroxz.exercise.service.ExerciseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
TODO: Benötigt später ein Refectoring.
 */

public class ExerciseCreator
{

    private final Scanner scanner = new Scanner(System.in);
    private final ExerciseService exerciseService;
    public ExerciseCreator(ExerciseService exerciseService)
    {
        this.exerciseService = exerciseService;
    }

    public void createOwnExercise()
    {
        System.out.println("Eigene Übung erstellen:");

        try
        {
            System.out.print("Name der Übung: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty())
            {
                System.out.println("Fehler: Der Name darf nicht leer sein.");
                return;
            }

            System.out.print("Kategorie (STRENGTH, CARDIO, MOBILITY): ");
            ExerciseCategory category = getCategoryInput();

            System.out.print("Schwierigkeit (EASY, MEDIUM, HARD): ");
            Difficulty difficulty = getDifficultyInput();

            System.out.print("Beschreibung der Übung: ");
            String description = scanner.nextLine().trim();

            List<MuscleGroup> targetMuscles = getTargetMuscles();

            Exercise newExercise = new Exercise(
                    generateId(),
                    name,
                    category,
                    difficulty,
                    description,
                    targetMuscles
            );
            exerciseService.createExercise(newExercise);
            System.out.println("Erfolgreich ersptellt: " + newExercise);

        } catch (Exception e)
        {
            System.out.println("Fehler beim Erstellen der Übung: " + e.getMessage());
        }
    }

    private ExerciseCategory getCategoryInput()
    {
        while (true)
        {
            try
            {
                String input = scanner.nextLine().trim().toUpperCase();
                return ExerciseCategory.valueOf(input);
            } catch (IllegalArgumentException e)
            {
                System.out.print("Ungültige Kategorie. Bitte erneut eingeben (STRENGTH, CARDIO, MOBILITY): ");
            }
        }
    }

    private Difficulty getDifficultyInput()
    {
        while (true)
        {
            try
            {
                String input = scanner.nextLine().trim().toUpperCase();
                return Difficulty.valueOf(input);
            } catch (IllegalArgumentException e)
            {
                System.out.print("Ungültige Schwierigkeit. Bitte erneut eingeben (EASY, MEDIUM, HARD): ");
            }
        }
    }

    private List<MuscleGroup> getTargetMuscles()
    {
        List<MuscleGroup> muscles = new ArrayList<>();
        System.out.println("Geben Sie die Muskelgruppen ein (z. B. CHEST, BACK, LEGS), mit Komma getrennt:");
        String input = scanner.nextLine().trim().toUpperCase();
        if (!input.isEmpty())
        {
            String[] muscleArray = input.split(",");
            for (String muscle : muscleArray)
            {
                try
                {
                    muscles.add(MuscleGroup.valueOf(muscle.trim()));
                } catch (IllegalArgumentException e)
                {
                    System.out.println("Warnung: Ungültige Muskelgruppe übersprungen -> " + muscle.trim());
                }
            }
        }
        return muscles;
    }

    private int generateId()
    {
        return (int) (Math.random() * 10000); // Dummy-Implementierung für eine zufällige ID
    }
}
