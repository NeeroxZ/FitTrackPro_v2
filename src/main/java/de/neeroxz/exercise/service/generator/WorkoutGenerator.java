package de.neeroxz.exercise.service.generator;

import de.neeroxz.exercise.model.Exercise;
import de.neeroxz.exercise.model.TrainingSplit;
import de.neeroxz.exercise.model.Workout;
import de.neeroxz.exercise.model.WorkoutType;

import java.util.List;

public interface WorkoutGenerator {
    List<Workout> generateWorkout(String baseName, WorkoutType type, TrainingSplit split, int frequency, String username);
}
