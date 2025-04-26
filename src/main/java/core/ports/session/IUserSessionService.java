package core.ports.session;

import core.domain.user.User;

import java.util.Optional;

public interface IUserSessionService
{

    /**
     * Gibt den aktuellen eingeloggten Benutzer als Optional zurück.
     * Falls kein Benutzer eingeloggt ist, gibt es ein leeres Optional zurück.
     */
    Optional<User> getLoggedInUser();

    /**
     * Setzt den eingeloggten Benutzer.
     */
    boolean isLoggedIn();

    /**
     * Meldet den aktuellen Benutzer ab.
     */
    void logout();

    void setLoggedInUser(User updatedUser);

    String getCurrentUsername();

}
