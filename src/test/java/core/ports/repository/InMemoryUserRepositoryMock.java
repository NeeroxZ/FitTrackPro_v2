package core.ports.repository;

import core.domain.user.Password;
import core.domain.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepositoryMock implements IUserRepository {

    private final Map<String, User> users = new HashMap<>();

    @Override
    public Optional<User> findUserByUsernameAndPassword(String username, Password password) {
        return users.values().stream()
                    .filter(u -> u.username().equals(username)
                            && u.password().getHashedPassword().equals(password.getHashedPassword()))
                    .findFirst();
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public void saveUser(User user) {
        users.put(user.username(), user);
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user.username());
    }

    // Hilfsmethoden für Tests
    public void add(User user) {
        users.put(user.username(), user);
    }

    public User get(String username) {
        return users.get(username);
    }

    public void clear() {
        users.clear();
    }

    public int count() {
        return users.size();
    }
}
