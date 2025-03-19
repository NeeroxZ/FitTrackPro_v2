package adapters.session;

import core.domain.user.User;
import core.ports.session.IUserSessionService;

import java.util.Optional;

public class LoggedInUser implements IUserSessionService {

    private User currentUser;

    @Override
    public Optional<User> getLoggedInUser() {
        return Optional.ofNullable(currentUser);
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

    //todo exception
    @Override
    public String getCurrentUsername()
    {
        return getLoggedInUser()
                .map(User::username)
                .orElseThrow(() -> new UserNotLoggedInException("Kein Benutzer eingeloggt!"));
    }

    public void login(User user) {
        this.currentUser = user;
    }
}
