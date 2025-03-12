package de.neeroxz.exercise.service.generator;

import de.neeroxz.exercise.model.*;
import de.neeroxz.exercise.service.ExerciseService;

import java.util.*;
import java.util.stream.Collectors;


public class SmarterWorkoutGenerator implements WorkoutGenerator
{

    private final ExerciseService exerciseService;
    private final Random random = new Random();

    public SmarterWorkoutGenerator(ExerciseService exerciseService)
    {
        this.exerciseService = exerciseService;
    }


    @Override
    public List<Workout> generateWorkout(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username)
    {
        List<Exercise> allExercises = exerciseService.getExercisesByType(type);
        if (allExercises.isEmpty())
        {
            throw new RuntimeException("Keine passenden Übungen gefunden!");
        }

        List<Workout> weeklyWorkouts = new ArrayList<>();

        for (int i = 0; i < frequency; i++)
        {
            String workoutName = baseName + " - Einheit " + (i + 1);
            List<Exercise> selectedExercises = selectExercisesForDay(allExercises, split, i);

            Workout workout = new Workout(
                    0,
                    workoutName,
                    type,
                    selectedExercises,
                    username,
                    frequency,
                    split
            );
            weeklyWorkouts.add(workout);
        }
        return weeklyWorkouts;
    }

    private List<Exercise> selectExercisesForDay(List<Exercise> exercises, TrainingSplit split, int dayIndex)
    {
        return switch (split)
        {
            case GANZKORPER -> getFullBodyWorkout(exercises);
            case OBER_UNTER -> getUpperLowerWorkout(exercises, dayIndex);
            case PUSH_PULL_LEG -> getPushPullLegWorkout(exercises, dayIndex);
            default -> getRandomExercises(exercises, 3); // Fallback für unbekannte Splits
        };
    }

    private List<Exercise> getRandomExercises(List<Exercise> exercises, int i)
    {
        return null;
    }

    private List<Exercise> getFullBodyWorkout(List<Exercise> exercises)
    {
        return filterAndSelectExercises(exercises,
                                        6,
                                        "BRUST",
                                        "RUECKEN",
                                        "SCHULTER",
                                        "BEINE",
                                        "CORE");
    }

    private List<Exercise> getUpperLowerWorkout(List<Exercise> exercises, int dayIndex)
    {
        if (dayIndex % 2 == 0)
        { // Gerade Tage: Oberkörper
            return filterAndSelectExercises(exercises,
                                            5,
                                            "BRUST",
                                            "RUECKEN",
                                            "SCHULTER",
                                            "ARMS");
        }
        else
        { // Ungerade Tage: Unterkörper
            return filterAndSelectExercises(exercises, 5, "BEINE", "CORE");
        }
    }

    private List<Exercise> getPushPullLegWorkout(List<Exercise> exercises, int dayIndex)
    {
        return switch (dayIndex % 3)
        {
            case 0 -> filterAndSelectExercises(exercises, 5, "BRUST", "SCHULTER", "TRIZEPS"); // Push
            case 1 -> filterAndSelectExercises(exercises, 5, "RUECKEN", "BIZEPS"); // Pull
            default -> filterAndSelectExercises(exercises, 5, "BEINE", "CORE"); // Leg
        };
    }

    private List<Exercise> filterAndSelectExercises(List<Exercise> exercises, int count, String... categories)
    {
        Set<String> categorySet = new HashSet<>(Arrays.asList(categories));
        List<Exercise> filtered = exercises.stream()
                                           .filter(e -> categorySet.contains(e.category().toString()))
                                           .collect(Collectors.toList());

        Collections.shuffle(filtered); // Mischen für mehr Variation
        return filtered.stream().limit(count).collect(Collectors.toList());
    }

}
