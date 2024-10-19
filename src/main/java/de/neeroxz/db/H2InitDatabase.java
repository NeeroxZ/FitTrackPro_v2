package de.neeroxz.db;

/*
 * Class: H2InitDatabase
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class H2InitDatabase {

    public void initDatabase() {
        Connection connection = null;
        try {
            // Überprüfen, ob die Datenbankdatei bereits existiert
            File dbFile = new File("./fittracker.mv.db");
            if (dbFile.exists()) {
                System.out.println("Datenbank existiert bereits. Keine Initialisierung erforderlich.");
                return; // Falls die Datei existiert, die Initialisierung überspringen
            }

            // Verbindung zur H2-Datenbank im File-Modus herstellen
            String url = "jdbc:h2:./fittracker"; // Die Datei "fittracker.mv.db" wird im aktuellen Verzeichnis gespeichert
            connection = DriverManager.getConnection(url, "sa", ""); // Benutzername: 'sa', Passwort: leer

            System.out.println("Datenbank wird initialisiert...");

            // Tabelle "users" erstellen
            String createUserTable = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "username VARCHAR(255) NOT NULL, "
                    + "password VARCHAR(255) NOT NULL)";
            try (PreparedStatement stmt = connection.prepareStatement(createUserTable)) {
                stmt.execute();
            }

            // Tabelle "exercises" erstellen
            String createExerciseTable = "CREATE TABLE IF NOT EXISTS exercises ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "category VARCHAR(255) NOT NULL, "
                    + "difficulty VARCHAR(50) NOT NULL)";
            try (PreparedStatement stmt = connection.prepareStatement(createExerciseTable)) {
                stmt.execute();
            }

            // Tabelle "workouts" erstellen
            String createWorkoutTable = "CREATE TABLE IF NOT EXISTS workouts ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT, "
                    + "name VARCHAR(255) NOT NULL)";
            try (PreparedStatement stmt = connection.prepareStatement(createWorkoutTable)) {
                stmt.execute();
            }

            // Tabelle "user_workouts" erstellen (Verknüpfung User und Workouts)
            String createUserWorkoutsTable = "CREATE TABLE IF NOT EXISTS user_workouts ("
                    + "user_id INT, "
                    + "workout_id INT, "
                    + "exercise_id INT, "
                    + "FOREIGN KEY (user_id) REFERENCES users(id), "
                    + "FOREIGN KEY (workout_id) REFERENCES workouts(id), "
                    + "FOREIGN KEY (exercise_id) REFERENCES exercises(id))";
            try (PreparedStatement stmt = connection.prepareStatement(createUserWorkoutsTable)) {
                stmt.execute();
            }

            // Übungen (Kraftsport, Cardio, Yoga) in die Tabelle exercises einfügen
            String insertExercises = "INSERT INTO exercises (name, category, difficulty) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(insertExercises)) {
                // Kraftsport Übungen
                stmt.setString(1, "Bench Press");
                stmt.setString(2, "Brust");
                stmt.setString(3, "Mittel");
                stmt.executeUpdate();

                stmt.setString(1, "Incline Bench Press");
                stmt.setString(2, "Brust");
                stmt.setString(3, "Schwer");
                stmt.executeUpdate();

                stmt.setString(1, "Chest Fly");
                stmt.setString(2, "Brust");
                stmt.setString(3, "Leicht");
                stmt.executeUpdate();

                // Weitere Übungen hinzufügen, hier als Beispiel...
                stmt.setString(1, "Push-up");
                stmt.setString(2, "Brust");
                stmt.setString(3, "Leicht");
                stmt.executeUpdate();

                stmt.setString(1, "Dips");
                stmt.setString(2, "Brust");
                stmt.setString(3, "Mittel");
                stmt.executeUpdate();

                // Cardio Übungen
                stmt.setString(1, "Jogging");
                stmt.setString(2, "Cardio");
                stmt.setString(3, "Mittel");
                stmt.executeUpdate();

                stmt.setString(1, "Cycling");
                stmt.setString(2, "Cardio");
                stmt.setString(3, "Mittel");
                stmt.executeUpdate();

                stmt.setString(1, "Swimming");
                stmt.setString(2, "Cardio");
                stmt.setString(3, "Schwer");
                stmt.executeUpdate();

                // Yoga Übungen
                stmt.setString(1, "Mountain Pose");
                stmt.setString(2, "Yoga");
                stmt.setString(3, "Leicht");
                stmt.executeUpdate();

                stmt.setString(1, "Downward Dog");
                stmt.setString(2, "Yoga");
                stmt.setString(3, "Mittel");
                stmt.executeUpdate();

                stmt.setString(1, "Tree Pose");
                stmt.setString(2, "Yoga");
                stmt.setString(3, "Leicht");
                stmt.executeUpdate();
            }

            System.out.println("Datenbank initialisiert und Übungen hinzugefügt.");

            // Teste die Einträge in der Tabelle "exercises"
            String queryExercises = "SELECT * FROM exercises";
            try (PreparedStatement stmt = connection.prepareStatement(queryExercises);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name")
                            + ", Kategorie: " + rs.getString("category") + ", Schwierigkeit: " + rs.getString("difficulty"));
                }
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception ex) {
                ex.fillInStackTrace();
            }
        }
    }
}

