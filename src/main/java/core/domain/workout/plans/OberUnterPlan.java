package core.domain.workout.plans;

import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.TrainingSplit;

import java.util.*;


public class OberUnterPlan implements IWorkoutPlan {

    @Override
    public TrainingSplit appliesTo() {
        return TrainingSplit.OBER_UNTER;
    }

    @Override
    public List<Exercise> selectExercises(List<Exercise> pool, int dayIndex) {
        if (dayIndex % 2 == 0) {
            return filter(pool, ExerciseCategory.BRUST, ExerciseCategory.BRUST,ExerciseCategory.RUECKEN, ExerciseCategory.TRIZEPS, ExerciseCategory.BIZEPS, ExerciseCategory.SEITLICHE_SCHULTER);
        } else {
            return filter(pool, ExerciseCategory.BEINE, ExerciseCategory.CORE);
        }
    }

    private List<Exercise> filter(List<Exercise> exercises, ExerciseCategory... categories) {
        List<Exercise> result = new ArrayList<>();
        Random random = new Random();

        for (ExerciseCategory category : categories) {
            List<Exercise> matching = exercises.stream()
                                               .filter(e -> e.category() == category)
                                               .toList();

            if (!matching.isEmpty()) {
                result.add(matching.get(random.nextInt(matching.size())));
            }
        }

        return result;
    }

}
