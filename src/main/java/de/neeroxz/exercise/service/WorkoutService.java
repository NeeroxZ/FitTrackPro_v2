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
import de.neeroxz.user.session.LoggedInUser;
import de.neeroxz.user.model.User;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WorkoutService
{
    private final WorkoutRepository workoutRepository;
    private final ExerciseService exerciseService;

    public WorkoutService(WorkoutRepository workoutRepository, ExerciseService exerciseService)
    {
        this.workoutRepository = workoutRepository;
        this.exerciseService = exerciseService;
    }

    /**
     * Bestehende Methode für random Workouts ohne zusätzliche Parameter.
     */
    public Workout createRandomWorkout(String name, WorkoutType type)
    {
        // Fallback, falls keine zusätzlichen Parameter benötigt werden.
        return createRandomWorkout(name, type, 3, null); // z.B. Standardwert: 3 Trainingstage/Woche, kein Split
    }

    /**
     * Neue Methode, die zusätzlich die Trainingsfrequenz und den Trainings-Split entgegennimmt.
     */
    public Workout createRandomWorkout(String name, WorkoutType type, int frequency, TrainingSplit split)
    {
        // 🔥 Check: Ist ein Benutzer eingeloggt?
        User currentUser = LoggedInUser
                .getCurrentUser()
                .orElseThrow(() -> new RuntimeException("Kein Benutzer eingeloggt!"));

        // 🔥 Hol alle passenden Übungen für den Typ
        List<Exercise> exercises = exerciseService.getExercisesByType(type);
        if (exercises.size() < 4)
        {
            throw new RuntimeException("Nicht genug Übungen in der Datenbank!");
        }

        // 🎲 Zufällige 4 Übungen wählen
        Random random = new Random();
        List<Exercise> selectedExercises = random
                .ints(0, exercises.size())
                .distinct()
                .limit(4)
                .mapToObj(exercises::get)
                .collect(Collectors.toList());

        // 📌 Neues Workout erstellen & speichern
        Workout workout = new Workout(
                0,
                name,
                type,
                selectedExercises,
                currentUser.username(),
                frequency,
                split
        );
        workoutRepository.saveWorkout(workout);

        // todo Optional: Ausgabe der ausgewählten Übungen zur Kontrolle
        for (Exercise e : workout.exercises())
        {
            System.out.println(e.name());
        }
        return workout;
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
}
