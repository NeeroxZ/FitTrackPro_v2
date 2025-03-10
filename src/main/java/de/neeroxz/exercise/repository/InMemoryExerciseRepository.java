package de.neeroxz.exercise.repository;

/*
 * Class: InMemoryExerciseRepository
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import de.neeroxz.exercise.model.Difficulty;
import de.neeroxz.exercise.model.Exercise;
import de.neeroxz.exercise.model.ExerciseCategory;
import de.neeroxz.exercise.model.MuscleGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryExerciseRepository implements ExerciseRepository
{
    private final List<Exercise> exercises = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    public InMemoryExerciseRepository()
    {
        // Initiale Übungen hinzufügen (statt SQL Inserts)

        // Brust (CHEST)
        addExercise("Bench Press", ExerciseCategory.BRUST, Difficulty.MEDIUM,
                    "Die klassische Übung für die Brust. Langhantel mit kontrollierter Bewegung nach unten und nach oben drücken.",
                    List.of(MuscleGroup.CHEST));

        addExercise("Incline Bench Press", ExerciseCategory.BRUST, Difficulty.HARD,
                    "Bank leicht schräg stellen, um den oberen Brustbereich stärker zu aktivieren.",
                    List.of(MuscleGroup.CHEST, MuscleGroup.SHOULDERS));

        addExercise("Chest Fly", ExerciseCategory.BRUST, Difficulty.EASY,
                    "Isolationsübung für die Brust. Arme mit leichter Beugung ausbreiten und wieder zusammenführen.",
                    List.of(MuscleGroup.CHEST));

        addExercise("Push-up", ExerciseCategory.BRUST, Difficulty.EASY,
                    "Klassische Liegestütze, um die Brust, Schultern und Arme zu stärken.",
                    List.of(MuscleGroup.CHEST, MuscleGroup.ARMS, MuscleGroup.CORE));

        addExercise("Dips", ExerciseCategory.BRUST, Difficulty.MEDIUM,
                    "Übung für Brust und Trizeps, indem man sich an parallelen Stangen hochdrückt.",
                    List.of(MuscleGroup.CHEST, MuscleGroup.ARMS));

        // Rücken (BACK)
        addExercise("Deadlift", ExerciseCategory.RUECKEN, Difficulty.HARD,
                    "Kraftvolle Ganzkörperübung, die primär den unteren Rücken und die hintere Kette stärkt.",
                    List.of(MuscleGroup.BACK, MuscleGroup.LEGS, MuscleGroup.CORE));

        addExercise("Pull-up", ExerciseCategory.RUECKEN, Difficulty.HARD,
                    "Klimmzüge sind eine der besten Übungen für den Latissimus und den gesamten oberen Rücken.",
                    List.of(MuscleGroup.BACK, MuscleGroup.ARMS));

        addExercise("Lat Pulldown", ExerciseCategory.RUECKEN, Difficulty.MEDIUM,
                    "Gezieltes Training des Latissimus mit einer Latzug-Maschine.",
                    List.of(MuscleGroup.BACK));

        addExercise("Bent-over Row", ExerciseCategory.RUECKEN, Difficulty.MEDIUM,
                    "Langhantel-Rudern im vorgebeugten Stand zur Stärkung des gesamten Rückens.",
                    List.of(MuscleGroup.BACK));

        addExercise("Face Pull", ExerciseCategory.RUECKEN, Difficulty.EASY,
                    "Mit Seilzug ausgeführte Übung zur Stärkung der hinteren Schultern und des oberen Rückens.",
                    List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS));

        // Bizeps (ARMS)
        addExercise("Bicep Curl", ExerciseCategory.BIZEPS, Difficulty.EASY,
                    "Klassische Isolationsübung für die Bizepsmuskulatur mit Kurzhanteln oder Langhantel.",
                    List.of(MuscleGroup.ARMS));

        addExercise("Hammer Curl", ExerciseCategory.BIZEPS, Difficulty.EASY,
                    "Variation der Bizeps-Curls mit neutralem Griff, um auch den Brachialis zu aktivieren.",
                    List.of(MuscleGroup.ARMS));

        addExercise("Preacher Curl", ExerciseCategory.BIZEPS, Difficulty.MEDIUM,
                    "Bizeps-Curls auf einer Scott-Bank zur vollständigen Isolation des Muskels.",
                    List.of(MuscleGroup.ARMS));

        // Trizeps (ARMS)
        addExercise("Triceps Dips", ExerciseCategory.TRIZEPS, Difficulty.MEDIUM,
                    "Körpergewicht-Übung für den Trizeps, ausgeführt an parallelen Stangen.",
                    List.of(MuscleGroup.ARMS));

        addExercise("Close-grip Bench Press", ExerciseCategory.TRIZEPS, Difficulty.HARD,
                    "Enges Bankdrücken zur gezielten Stimulation des Trizeps.",
                    List.of(MuscleGroup.CHEST, MuscleGroup.ARMS));

        addExercise("Overhead Triceps Extension", ExerciseCategory.TRIZEPS, Difficulty.EASY,
                    "Kurzhantel oder Kabelzug über dem Kopf für eine intensive Dehnung des Trizeps.",
                    List.of(MuscleGroup.ARMS));

        // Schultern (SHOULDERS)
        addExercise("Overhead Press", ExerciseCategory.VORDERE_SCHULTER, Difficulty.HARD,
                    "Langhantel oder Kurzhantel über den Kopf drücken, um die Schultermuskulatur zu stärken.",
                    List.of(MuscleGroup.SHOULDERS));

        addExercise("Front Raise", ExerciseCategory.VORDERE_SCHULTER, Difficulty.EASY,
                    "Isolationsübung für die vordere Schulter mit Kurzhanteln oder Kabelzug.",
                    List.of(MuscleGroup.SHOULDERS));

        addExercise("Lateral Raise", ExerciseCategory.SEITLICHE_SCHULTER, Difficulty.EASY,
                    "Seitliches Anheben der Arme mit Kurzhanteln für die mittlere Schultermuskulatur.",
                    List.of(MuscleGroup.SHOULDERS));

        addExercise("Reverse Fly", ExerciseCategory.HINTERE_SCHULTER, Difficulty.EASY,
                    "Rückwärtsfliegen mit Kurzhanteln zur Stärkung der hinteren Schultermuskulatur.",
                    List.of(MuscleGroup.SHOULDERS, MuscleGroup.BACK));

        // Beine (LEGS)
        addExercise("Squats", ExerciseCategory.BEINE, Difficulty.HARD,
                    "Kniebeugen sind eine der effektivsten Übungen für die gesamte Beinmuskulatur.",
                    List.of(MuscleGroup.LEGS, MuscleGroup.CORE));

        addExercise("Lunges", ExerciseCategory.BEINE, Difficulty.MEDIUM,
                    "Ausfallschritte zur Stärkung der Beine und des Gleichgewichts.",
                    List.of(MuscleGroup.LEGS, MuscleGroup.CORE));

        addExercise("Leg Press", ExerciseCategory.BEINE, Difficulty.MEDIUM,
                    "Beinstreckübung mit einer Maschine zur gezielten Belastung der Oberschenkelmuskulatur.",
                    List.of(MuscleGroup.LEGS));

        addExercise("Calf Raises", ExerciseCategory.BEINE, Difficulty.EASY,
                    "Wadenheben zur gezielten Stärkung der Wadenmuskulatur.",
                    List.of(MuscleGroup.LEGS));

        // Cardio (CARDIO)
        addExercise("Jogging", ExerciseCategory.CARDIO, Difficulty.MEDIUM,
                    "Klassisches Ausdauertraining zur Verbesserung der kardiovaskulären Fitness.",
                    List.of(MuscleGroup.LEGS, MuscleGroup.CORE));

        addExercise("Cycling", ExerciseCategory.CARDIO, Difficulty.MEDIUM,
                    "Radfahren ist eine gelenkschonende Ausdauerübung für die Beine.",
                    List.of(MuscleGroup.LEGS));

        addExercise("Swimming", ExerciseCategory.CARDIO, Difficulty.HARD,
                    "Ganzkörpertraining mit minimaler Belastung für die Gelenke.",
                    List.of(MuscleGroup.CHEST, MuscleGroup.BACK, MuscleGroup.ARMS, MuscleGroup.LEGS, MuscleGroup.CORE));

        addExercise("Jump Rope", ExerciseCategory.CARDIO, Difficulty.MEDIUM,
                    "Springseiltraining für Ausdauer und Koordination.",
                    List.of(MuscleGroup.LEGS, MuscleGroup.CORE));

        // Yoga (YOGA)
        addExercise("Mountain Pose", ExerciseCategory.YOGA, Difficulty.EASY,
                    "Grundlegende Haltung für eine verbesserte Körperhaltung und Balance.",
                    List.of(MuscleGroup.CORE));

        addExercise("Downward Dog", ExerciseCategory.YOGA, Difficulty.MEDIUM,
                    "Dehnt die hintere Kette und stärkt Schultern sowie Rumpf.",
                    List.of(MuscleGroup.BACK, MuscleGroup.SHOULDERS, MuscleGroup.LEGS));

        addExercise("Tree Pose", ExerciseCategory.YOGA, Difficulty.EASY,
                    "Gleichgewichtsübung zur Verbesserung der Stabilität.",
                    List.of(MuscleGroup.CORE, MuscleGroup.LEGS));
    }


    private void addExercise(String name,
                             ExerciseCategory category,
                             Difficulty difficulty,
                             String description,
                             List<MuscleGroup> targetMuscles
                            )
    {
        exercises.add(
                new Exercise(
                        idCounter.getAndIncrement(),
                        name,
                        category,
                        difficulty,
                        description,
                        targetMuscles
                )
                     );
    }

    @Override
    public Optional<Exercise> findById(int id)
    {
        return exercises
                .stream()
                .filter(e -> e.id() == id)
                .findFirst();
    }

    @Override
    public List<Exercise> findAll()
    {
        return new ArrayList<>(exercises);
    }
}
