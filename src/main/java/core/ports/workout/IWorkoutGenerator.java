package core.ports.workout;

import core.domain.exercise.TrainingSplit;
import core.domain.exercise.Workout;
import core.domain.exercise.WorkoutType;

import java.util.List;

public interface IWorkoutGenerator
{
    List<Workout> generateWorkout(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username);

}
