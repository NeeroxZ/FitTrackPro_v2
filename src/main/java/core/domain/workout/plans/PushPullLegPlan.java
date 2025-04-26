package core.domain.workout.plans;

import core.domain.exercise.Exercise;
import core.domain.exercise.ExerciseCategory;
import core.domain.exercise.TrainingSplit;

import java.util.*;

public class PushPullLegPlan implements IWorkoutPlan
{

    @Override
    public TrainingSplit appliesTo() {
        return TrainingSplit.PUSH_PULL_LEG;
    }

    @Override
    public List<Exercise> selectExercises(List<Exercise> pool, int dayIndex) {
        return switch (dayIndex % 3) {
            case 0 -> filter(pool, ExerciseCategory.BRUST,ExerciseCategory.BRUST,ExerciseCategory.BRUST, ExerciseCategory.SEITLICHE_SCHULTER, ExerciseCategory.TRIZEPS);
            case 1 -> filter(pool,  ExerciseCategory.RUECKEN,ExerciseCategory.RUECKEN,ExerciseCategory.RUECKEN, ExerciseCategory.BIZEPS);
            default -> filter(pool,  ExerciseCategory.BEINE,ExerciseCategory.BEINE,ExerciseCategory.BEINE, ExerciseCategory.CORE);
        };
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
