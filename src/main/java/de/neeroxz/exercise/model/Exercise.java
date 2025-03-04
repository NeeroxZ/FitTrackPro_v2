package de.neeroxz.exercise.model;

import java.util.List;

/**
 * Record: Exercise
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */

public record Exercise(
        int id,
        String name,
        ExerciseCategory category,
        Difficulty difficulty,
        String description,          // Detaillierte Beschreibung der Übung
        List<MuscleGroup> targetMuscles,  // Primär trainierte Muskelgruppen
        Equipment equipment,         // Benötigtes Equipment
        int estimatedDuration        // Geschätzte Dauer in Minuten
) {}


