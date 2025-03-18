package de.neeroxz.adapters.session;

import de.neeroxz.core.domain.user.User;
import de.neeroxz.core.ports.session.IUserSessionService;

import java.util.Optional;

public class LoggedInUser implements IUserSessionService {

    private User currentUser;

    @Override
    public Optional<String> getLoggedInUsername() {
        return Optional.ofNullable(currentUser).map(User::username);
    }

    @Override
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    public void login(User user) {
        this.currentUser = user;
    }
}
