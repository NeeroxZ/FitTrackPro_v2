package core.domain.workout.plans;

import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.TrainingSplit;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GanzkoerperPlan implements IWorkoutPlan {

    @Override
    public TrainingSplit appliesTo() {
        return TrainingSplit.GANZKORPER;
    }

    @Override
    public List<Exercise> selectExercises(List<Exercise> pool, int dayIndex) {
        return filter(pool, 8, ExerciseCategory.BRUST, ExerciseCategory.RUECKEN, ExerciseCategory.BEINE, ExerciseCategory.TRIZEPS, ExerciseCategory.BIZEPS, ExerciseCategory.CORE);
    }

    private List<Exercise> filter(List<Exercise> pool, int count, ExerciseCategory... categories) {
        Set<ExerciseCategory> catSet = Set.of(categories);
        List<Exercise> result = pool.stream().filter(e -> catSet.contains(e.category())).toList();
        Collections.shuffle(result);
        return result.stream().limit(count).toList();
    }
}

