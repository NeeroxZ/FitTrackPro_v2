package core.ports.workout;

import core.domain.exercise.TrainingDay;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;

import java.util.List;

public interface ICustomWorkoutGenerator {
    Workout create(String name, WorkoutType type, List<TrainingDay> days, String username);
}