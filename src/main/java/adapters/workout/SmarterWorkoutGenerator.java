package adapters.workout;

import core.domain.exercise.*;
import core.ports.repository.IExerciseRepository;
import core.ports.workout.IWorkoutGenerator;
import util.IdGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class SmarterWorkoutGenerator implements IWorkoutGenerator
{

    private final IExerciseRepository exerciseRepository;
    private final Random random = new Random();

    public SmarterWorkoutGenerator(IExerciseRepository exerciseRepository)
    {
        this.exerciseRepository = exerciseRepository;
    }

    @Override
    public List<Workout> generateWorkout(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username)
    {
        Optional<List<Exercise>> allExercises = exerciseRepository.findByType(type);// ✅ Kein direkter Service-Zugriff mehr!
        if (allExercises.isEmpty())
        {
            throw new RuntimeException("Keine passenden Übungen gefunden!");
        }

        List<TrainingDay> trainingDays = new ArrayList<>();

        for (int i = 0; i < frequency; i++)
        {
            String dayName = baseName + " - Tag " + (i + 1);
            List<Exercise> selectedExercises = selectExercisesForDay(allExercises.get(), split, i);

            trainingDays.add(new TrainingDay(dayName, selectedExercises));
        }

        int workoutId = IdGenerator.generateUniqueId();
        return List.of(new Workout(workoutId, baseName, type, trainingDays, username, frequency, split));
    }


    private List<Exercise> selectExercisesForDay(List<Exercise> exercises, TrainingSplit split, int dayIndex)
    {
        return switch (split)
        {
            case GANZKORPER -> getFullBodyWorkout(exercises);
            case OBER_UNTER -> getUpperLowerWorkout(exercises, dayIndex);
            case PUSH_PULL_LEG -> getPushPullLegWorkout(exercises, dayIndex);
            default -> getRandomExercises(exercises, 3);
        };
    }

    private List<Exercise> getRandomExercises(List<Exercise> exercises, int count)
    {
        Collections.shuffle(exercises);
        return exercises.stream().limit(count).toList();
    }

    private List<Exercise> filterAndSelectExercises(List<Exercise> exercises, int count, ExerciseCategory... categories)
    {
        Set<ExerciseCategory> categorySet = new HashSet<>(Arrays.asList(categories));
        List<Exercise> filtered = exercises.stream()
                                           .filter(e -> categorySet.contains(e.category()))
                                           .collect(Collectors.toList());

        Collections.shuffle(filtered);
        return filtered.stream().limit(count).toList();
    }

    private List<Exercise> getFullBodyWorkout(List<Exercise> exercises)
    {
        List<Exercise> upperBody = getUpperLowerWorkout(exercises, 0);
        List<Exercise> lowerBody = getUpperLowerWorkout(exercises, 1);
        upperBody.addAll(lowerBody);
        return upperBody;
    }

    private List<Exercise> getUpperLowerWorkout(List<Exercise> exercises, int dayIndex)
    {
        return (dayIndex % 2 == 0)
                ? filterAndSelectExercises(exercises, 7, ExerciseCategory.BRUST, ExerciseCategory.RUECKEN, ExerciseCategory.TRIZEPS, ExerciseCategory.BIZEPS, ExerciseCategory.SEITLICHE_SCHULTER)
                : filterAndSelectExercises(exercises, 5, ExerciseCategory.BEINE, ExerciseCategory.CORE);
    }

    private List<Exercise> getPushPullLegWorkout(List<Exercise> exercises, int dayIndex)
    {
        return switch (dayIndex % 3)
        {
            case 0 ->
                    filterAndSelectExercises(exercises, 5, ExerciseCategory.BRUST, ExerciseCategory.BRUST, ExerciseCategory.BRUST, ExerciseCategory.SEITLICHE_SCHULTER, ExerciseCategory.TRIZEPS);
            case 1 ->
                    filterAndSelectExercises(exercises, 5, ExerciseCategory.RUECKEN, ExerciseCategory.RUECKEN, ExerciseCategory.RUECKEN, ExerciseCategory.BIZEPS, ExerciseCategory.BIZEPS);
            default -> filterAndSelectExercises(exercises, 5, ExerciseCategory.BEINE, ExerciseCategory.CORE);
        };
    }
}
