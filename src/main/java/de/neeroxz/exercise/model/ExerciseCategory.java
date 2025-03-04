package de.neeroxz.exercise.model;

/**
 * Enum: ExerciseCategory
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */

public enum ExerciseCategory {
    // Kraftsport Unterkategorien
    BRUST(WorkoutType.KRAFTSPORT),
    RUECKEN(WorkoutType.KRAFTSPORT),
    BEINE(WorkoutType.KRAFTSPORT),

    // Cardio-Kategorien
    CARDIO(WorkoutType.CARDIO),

    // Yoga-Kategorien
    YOGA(WorkoutType.YOGA);

    private final WorkoutType workoutType;

    ExerciseCategory(WorkoutType workoutType)
    {
        this.workoutType = workoutType;
    }

    public WorkoutType getWorkoutType()
    {
        return workoutType;
    }
}

