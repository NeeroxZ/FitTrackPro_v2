package de.neeroxz.core.domain.exercise;

/**
 * Enum: ExerciseCategory
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */

public enum ExerciseCategory
{
    // Kraftsport Unterkategorien
    BRUST(WorkoutType.KRAFTSPORT),
    RUECKEN(WorkoutType.KRAFTSPORT),
    BEINE(WorkoutType.KRAFTSPORT),
    BIZEPS(WorkoutType.KRAFTSPORT),
    TRIZEPS(WorkoutType.KRAFTSPORT),

    VORDERE_SCHULTER(WorkoutType.KRAFTSPORT),
    SEITLICHE_SCHULTER(WorkoutType.KRAFTSPORT),
    HINTERE_SCHULTER(WorkoutType.KRAFTSPORT),
    // Cardio-Kategorien
    CARDIO(WorkoutType.CARDIO),

    // Yoga-Kategorien
    YOGA(WorkoutType.YOGA),

    CORE(WorkoutType.KRAFTSPORT);

    private final WorkoutType workoutType;

    ExerciseCategory(WorkoutType workoutType)
    {
        this.workoutType = workoutType;
    }

    public WorkoutType getWorkoutType()
    {
        return workoutType;
    }

    public boolean matchesWorkoutType(WorkoutType type)
    {
        if (type == this.workoutType){
            return true;
        }
        return false;
    }
}

