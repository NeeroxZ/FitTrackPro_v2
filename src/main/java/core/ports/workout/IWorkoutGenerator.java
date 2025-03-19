package core.ports.workout;

import core.domain.exercise.TrainingSplit;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;

import java.util.List;

public interface IWorkoutGenerator
{
    List<Workout> generateWorkout(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username);

}
