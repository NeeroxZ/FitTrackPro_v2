package core.ports.session;

import core.domain.user.User;

import java.util.Optional;

/**
 * Simuliert einen eingeloggten Benutzer für Tests.
 */
public class InMemoryUserSessionServiceMock implements IUserSessionService {

    private User loggedInUser;

    @Override
    public Optional<User> getLoggedInUser() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    @Override
    public void logout() {
        loggedInUser = null;
    }

    @Override
    public void setLoggedInUser(User updatedUser) {
        this.loggedInUser = updatedUser;
    }

    @Override
    public String getCurrentUsername()
    {
        return loggedInUser.username();
    }

    @Override
    public void registerObserver(IUserSessionObserver observer)
    {

    }

    @Override
    public void unregisterObserver(IUserSessionObserver observer)
    {

    }
}
