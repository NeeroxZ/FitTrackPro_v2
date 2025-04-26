package core.ports.workout;

import core.domain.exercise.TrainingSplit;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;

import java.util.List;

public interface IRandomWorkoutGenerator
{
    List<Workout> generate(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username);

}
