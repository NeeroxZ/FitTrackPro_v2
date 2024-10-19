package de.neeroxz.user;

/**
 * Class: Passwort
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */

public class Password {
    private final String hashedPassword;

    // Private Konstruktor, der nur für gehashte Passwörter verwendet wird
    private Password(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    // Factory Methode für bereits gehashte Passwörter (aus der Datenbank oder dem Hasher)
    public static Password ofHashed(String hashedPassword) {
        return new Password(hashedPassword);
    }

    // Methode, um ein rohes Passwort zu hashen und in ein Password-Objekt zu verwandeln
    public static Password hash(String rawPassword, PasswordHasher hasher) {
        return hasher.hash(rawPassword);  // Verwendet den übergebenen Hasher
    }

    // Methode, um zu überprüfen, ob ein rohes Passwort dem gehashten Passwort entspricht
    public boolean matches(String rawPassword, PasswordHasher hasher) {
        Password hashedAttempt = hasher.hash(rawPassword);
        return this.hashedPassword.equals(hashedAttempt.hashedPassword);
    }

    // Getter für das gehashte Passwort, um es z.B. in der Datenbank zu speichern
    public String getHashedPassword() {
        return hashedPassword;
    }
    @Override
    public String toString(){
        return hashedPassword;
    }
}


