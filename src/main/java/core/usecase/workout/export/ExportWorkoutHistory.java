package core.usecase.workout.export;


import java.nio.file.Path;

import adapters.persistence.inmemory.InMemoryWorkoutRepository;
import core.domain.workout.Workout;

import java.nio.file.Files;
import java.util.List;

/**
 * Exportiert alle Workouts eines Users in eine simpel formatierte Textdatei.
 * Sieht sauber aus (Constructor-Injection) – verstößt aber gegen DIP,
 * weil der High-Level-Use-Case das konkrete In-Memory-Repo kennt.
 */
public class ExportWorkoutHistory {

    // compile-time Abhängigkeit auf LOW-LEVEL-Detail
    private final InMemoryWorkoutRepository repo;

    public ExportWorkoutHistory(InMemoryWorkoutRepository repo) {
        this.repo = repo;
    }

    public void export(String username, Path targetFile) {
        List<Workout> workouts = repo.findByUser(username);   // direkter Aufruf

        try (var writer = Files.newBufferedWriter(targetFile)) {
            for (Workout w : workouts) {
                writer.write("%d;%s;%s%n"
                                     .formatted(w.id(), w.name(), w.type()));
            }
        } catch (Exception ex) {
            throw new RuntimeException("Export fehlgeschlagen", ex);
        }
    }
}
