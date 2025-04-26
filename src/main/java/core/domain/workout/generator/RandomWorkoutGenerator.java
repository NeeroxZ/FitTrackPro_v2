package core.domain.workout.generator;

import core.domain.exercise.*;
import core.domain.workout.plans.IWorkoutPlan;
import core.domain.workout.Workout;
import core.domain.workout.WorkoutType;
import core.ports.repository.IExerciseRepository;
import core.ports.workout.IRandomWorkoutGenerator;
import util.IdGenerator;

import java.util.*;

public class RandomWorkoutGenerator implements IRandomWorkoutGenerator {

    private final IExerciseRepository exerciseRepository;
    private final Map<TrainingSplit, IWorkoutPlan> plans;

    public RandomWorkoutGenerator(IExerciseRepository exerciseRepository, List<IWorkoutPlan> strategies) {
        this.exerciseRepository = exerciseRepository;
        this.plans = new HashMap<>();
        for (IWorkoutPlan plan : strategies) {
            plans.put(plan.appliesTo(), plan);
        }
    }

    @Override
    public List<Workout> generate(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username) {
        List<Exercise> pool = exerciseRepository.findByType(type)
                                                .orElseThrow(() -> new RuntimeException("Keine Übungen für Typ: " + type));

        IWorkoutPlan plan = plans.get(split);
        System.out.println(plan.getClass().getSimpleName());
        List<TrainingDay> days = new ArrayList<>();

        for (int i = 0; i < frequency; i++) {
            List<Exercise> selected = plan.selectExercises(pool, i);
            String dayName = baseName + " - Tag " + (i + 1);
            days.add(new TrainingDay(dayName, selected));
        }

        return List.of(new Workout(
                IdGenerator.generateUniqueId(),
                baseName,
                type,
                days,
                username,
                frequency,
                split
        ));
    }
}
