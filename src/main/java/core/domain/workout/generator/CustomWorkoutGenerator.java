package core.domain.workout.generator;

import core.domain.exercise.TrainingDay;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.workout.ICustomWorkoutGenerator;

import java.util.List;

public class CustomWorkoutGenerator implements ICustomWorkoutGenerator
{
    @Override
    public Workout create(String name, WorkoutType type, List<TrainingDay> days, String username)
    {
        return null;
    }
}
