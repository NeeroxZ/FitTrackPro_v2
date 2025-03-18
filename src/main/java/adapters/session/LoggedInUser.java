package adapters.session;

import core.domain.user.User;
import core.ports.session.IUserSessionService;

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

    @Override
    public void setLoggedInUser(User updatedUser)
    {
        this.currentUser = updatedUser;
    }

    public void login(User user) {
        this.currentUser = user;
    }
}
