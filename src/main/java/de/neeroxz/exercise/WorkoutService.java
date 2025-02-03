package de.neeroxz.exercise;

/**
 * Class: WorkoutService
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import de.neeroxz.user.LoggedInUser;
import de.neeroxz.user.User;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    public Workout createRandomWorkout(String name, WorkoutType type) {
        // 🔥 Check: Ist ein Benutzer eingeloggt?
        User currentUser = LoggedInUser.getCurrentUser().orElseThrow(() -> new RuntimeException("Kein Benutzer eingeloggt!"));

        // 🔥 Hol alle passenden Übungen für den Typ
        List<Exercise> exercises = exerciseRepository.findAll().stream()
                .filter(e -> e.category().getWorkoutType() == type)
                .collect(Collectors.toList());

        if (exercises.size() < 4) {
            throw new RuntimeException("Nicht genug Übungen in der Datenbank!");
        }

        // 🎲 Zufällige 4 Übungen wählen
        Random random = new Random();
        List<Exercise> selectedExercises = random.ints(0, exercises.size())
                .distinct()
                .limit(4)
                .mapToObj(exercises::get)
                .collect(Collectors.toList());

        // 📌 Neues Workout erstellen & speichern
        Workout workout = new Workout(0, name, type, selectedExercises, currentUser.username());
        workoutRepository.saveWorkout(workout);

        for (Exercise e : workout.exercises()){
            System.out.println(e.name());
        }
        return workout;
    }

    public List<Workout> getUserWorkouts() {
        User currentUser = LoggedInUser.getCurrentUser().orElseThrow(() -> new RuntimeException("Kein Benutzer eingeloggt!"));
        return workoutRepository.findByUser(currentUser.username());
    }

    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    public void saveWorkout(Workout workout) {
        workoutRepository.saveWorkout(workout);
    }
}
