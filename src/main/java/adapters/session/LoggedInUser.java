package adapters.session;

import core.domain.user.User;
import core.ports.session.IUserSessionService;
import core.ports.session.IUserSessionObserver; // <<< neu

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoggedInUser implements IUserSessionService {

    private User currentUser;
    private final List<IUserSessionObserver> observers = new ArrayList<>(); // <<< Observer-Liste

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
        notifyLogoutObservers(); // <<< Benachrichtige hier die Observer!
    }

    @Override
    public void setLoggedInUser(User updatedUser) {
        this.currentUser = updatedUser;
    }

    @Override
    public String getCurrentUsername() {
        return getLoggedInUser()
                .map(User::username)
                .orElseThrow(() -> new UserNotLoggedInException("Kein Benutzer eingeloggt!"));
    }

    public void login(User user) {
        this.currentUser = user;
    }

    // --- Neu für Observer Pattern ---
    public void registerObserver(IUserSessionObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(IUserSessionObserver observer) {
        observers.remove(observer);
    }

    private void notifyLogoutObservers() {
        System.out.println("hier rufen wir auf");
        for (IUserSessionObserver observer : observers) {
            System.out.println(observer + " gibts hier nix ?");
            observer.onUserLogout();
        }
    }
}
