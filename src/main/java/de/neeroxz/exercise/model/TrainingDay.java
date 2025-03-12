package de.neeroxz.exercise.model;
import java.util.List;

public record TrainingDay(String name, List<Exercise> exercises) {
}