package de.neeroxz.core.ports.workout;

import de.neeroxz.core.domain.exercise.TrainingSplit;
import de.neeroxz.core.domain.exercise.Workout;
import de.neeroxz.core.domain.exercise.WorkoutType;

import java.util.List;

public interface IWorkoutGenerator
{
    List<Workout> generateWorkout(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username);

}
