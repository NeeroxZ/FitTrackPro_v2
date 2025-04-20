package core.domain.workout.plans;

import core.domain.exercise.Exercise;
import core.domain.exercise.TrainingSplit;

import java.util.List;

public interface IWorkoutPlan {
    TrainingSplit appliesTo();
    List<Exercise> selectExercises(List<Exercise> pool, int dayIndex);
}