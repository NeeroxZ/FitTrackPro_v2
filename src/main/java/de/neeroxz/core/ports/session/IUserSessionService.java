package de.neeroxz.core.ports.session;

import java.util.Optional;

public interface IUserSessionService
{

    /**
     * Gibt den aktuellen eingeloggten Benutzer als Optional zurück.
     * Falls kein Benutzer eingeloggt ist, gibt es ein leeres Optional zurück.
     */
    Optional<String> getLoggedInUsername();

    /**
     * Setzt den eingeloggten Benutzer.
     */
    boolean isLoggedIn();

    /**
     * Meldet den aktuellen Benutzer ab.
     */
    void logout();
}
