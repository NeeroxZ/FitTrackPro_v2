package de.neeroxz.exercise.service;

/*
 * Class: WorkoutService
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import de.neeroxz.exercise.model.Exercise;
import de.neeroxz.exercise.model.TrainingSplit;
import de.neeroxz.exercise.model.Workout;
import de.neeroxz.exercise.model.WorkoutType;
import de.neeroxz.exercise.repository.WorkoutRepository;
import de.neeroxz.exercise.service.generator.SmarterWorkoutGenerator;
import de.neeroxz.exercise.service.generator.WorkoutGenerator;
import de.neeroxz.user.session.LoggedInUser;
import de.neeroxz.user.model.User;

import java.util.List;
import java.util.UUID;

public class WorkoutService
{
    private final WorkoutRepository workoutRepository;
    private final ExerciseService exerciseService;
    private final WorkoutGenerator workoutGenerator;

    public WorkoutService(WorkoutRepository workoutRepository, ExerciseService exerciseService)
    {
        this.workoutRepository = workoutRepository;
        this.exerciseService = exerciseService;
        this.workoutGenerator = new SmarterWorkoutGenerator(exerciseService);

    }

    public List<Workout> createWorkouts(String name, WorkoutType type, int frequency, TrainingSplit split)
    {
        User currentUser = LoggedInUser.getCurrentUser()
                                       .orElseThrow(() -> new RuntimeException("Kein Benutzer eingeloggt!"));

        List<Workout> workouts = workoutGenerator.generateWorkout(name, type, split, frequency, currentUser.username());

        workouts.forEach(workoutRepository::saveWorkout);

        return workouts;
    }

    /**
     * Neue Methode, die zusätzlich die Trainingsfrequenz und den Trainings-Split entgegennimmt.
     */
    public List<Workout> createRandomWorkout(String name,
                                             WorkoutType type,
                                             int frequency,
                                             TrainingSplit split
                                            )
    {
        User currentUser = LoggedInUser.getCurrentUser()
                                       .orElseThrow(() -> new RuntimeException("Kein Benutzer eingeloggt!"));

        List<Workout> workouts = workoutGenerator.generateWorkout(name, type, split, frequency, currentUser.username());

        workouts.forEach(workoutRepository::saveWorkout);

        return workouts;
    }

    public List<Workout> getUserWorkouts()
    {
        User currentUser = LoggedInUser
                .getCurrentUser()
                .orElseThrow(() -> new RuntimeException("Kein Benutzer eingeloggt!"));
        return workoutRepository.findByUser(currentUser.username());
    }

    public List<Exercise> getAllExercises()
    {
        return exerciseService.getAllExercises();
    }

    public void saveWorkout(Workout workout)
    {
        workoutRepository.saveWorkout(workout);
    }

    public void removeWorkout(int id)
    {
        workoutRepository.deleteById(id);
    }

    public int generateUniqueWorkoutId()
    {
        return UUID.randomUUID().hashCode();
    }
}
