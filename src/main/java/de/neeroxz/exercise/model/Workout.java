package de.neeroxz.exercise.model;

/*
 * Record: Workout
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import de.neeroxz.exercise.service.generator.SmarterWorkoutGenerator;

import java.util.List;

public record Workout(
        int id,
        String name,
        WorkoutType type,
        List<TrainingDay> trainingDays,
        String username,
        int frequency,          // Anzahl Trainingstage pro Woche (1-7)
        TrainingSplit split)
{
}
