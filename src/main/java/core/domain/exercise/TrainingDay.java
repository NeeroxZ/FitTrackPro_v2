package core.domain.exercise;
import java.util.List;

public record TrainingDay(String name, List<Exercise> exercises) {
}