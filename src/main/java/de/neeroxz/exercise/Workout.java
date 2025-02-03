package de.neeroxz.exercise;

/**
 * Record: Workout
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */
import java.util.List;

public record Workout(int id, String name, WorkoutType type, List<Exercise> exercises, String username) {}
