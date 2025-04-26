package core.domain.user;

import core.ports.services.IPasswordHasher;

/**
 * Class: Password
 * <p>
 * Diese Klasse repräsentiert ein Passwort, das in gehashter Form gespeichert wird.
 * Sie bietet Methoden, um ein Passwort zu hashen, ein gehashtes Passwort zu erstellen
 * und ein rohes Passwort mit dem gehashten zu vergleichen.
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class Password {
    private final String hashedPassword;

    /**
     * Privater Konstruktor für die Erstellung eines Password-Objekts
     * mit einem bereits gehashten Passwort.
     *
     * @param hashedPassword Das bereits gehashte Passwort.
     */
    public Password(String hashedPassword)
    {
        this.hashedPassword = hashedPassword;
    }

    /**
     * Factory-Methode, um ein Password-Objekt mit einem bereits gehashten Passwort zu erstellen.
     * Diese Methode wird verwendet, wenn das gehashte Passwort bereits vorliegt (z.B. aus einer Datenbank).
     *
     * @param hashedPassword Das bereits gehashte Passwort.
     * @return Ein Password-Objekt mit dem gegebenen gehashten Passwort.
     */
    public static Password ofHashed(String hashedPassword)
    {
        return new Password(hashedPassword);
    }

    /**
     * Methode zum Hashen eines rohen Passworts. Das rohe Passwort wird
     * durch den angegebenen PasswordHasher in ein gehashtes Passwort umgewandelt.
     *
     * @param rawPassword Das rohe Passwort, das gehasht werden soll.
     * @param hasher      Der PasswordHasher, der zum Hashen des Passworts verwendet wird.
     * @return Ein Password-Objekt, das das gehashte Passwort enthält.
     */
    public static Password hash(String rawPassword, IPasswordHasher hasher)
    {
        return hasher.hash(rawPassword);  // Verwendet den übergebenen Hasher, um das Passwort zu hashen
    }

    /**
     * Methode zum Vergleichen eines rohen Passworts mit dem gespeicherten gehashten Passwort.
     * Das rohe Passwort wird gehasht, und dann wird geprüft, ob der Hash dem gespeicherten
     * gehashten Passwort entspricht.
     *
     * @param rawPassword Das rohe Passwort, das überprüft werden soll.
     * @param hasher      Der PasswordHasher, der zum Hashen des rohen Passworts verwendet wird.
     * @return true, wenn das gehashte rohe Passwort mit dem gespeicherten gehashten Passwort übereinstimmt,
     *         sonst false.
     */
    public boolean matches(String rawPassword, IPasswordHasher hasher)
    {
        Password hashedAttempt = hasher.hash(rawPassword);
        return this.hashedPassword.equals(hashedAttempt.hashedPassword);
    }

    /**
     * Getter-Methode, um das gehashte Passwort zurückzugeben.
     * Diese Methode kann z.B. verwendet werden, um das Passwort in einer Datenbank zu speichern.
     *
     * @return Das gehashte Passwort.
     */
    public String getHashedPassword()
    {
        return hashedPassword;
    }

    /**
     * Überschreibt die toString-Methode, um das gehashte Passwort als String zurückzugeben.
     *
     * @return Das gehashte Passwort als String.
     */
    @Override
    public String toString()
    {
        return hashedPassword;
    }
}
