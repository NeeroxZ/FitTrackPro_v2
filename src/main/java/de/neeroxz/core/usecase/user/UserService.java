package de.neeroxz.core.usecase.user;

import de.neeroxz.core.domain.user.Password;
import de.neeroxz.core.domain.user.User;
import de.neeroxz.core.ports.repository.IUserRepository;
import de.neeroxz.core.ports.services.IUserService;
import de.neeroxz.core.ports.session.IUserSessionService;

import java.util.Optional;

/**
 * Class: UserService
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IUserSessionService userSessionService;

    public UserService(IUserRepository userRepository, IUserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, Password password) {
        return userRepository.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public void deleteUser(User user) {
        userRepository.deleteUser(user);

        // Abmelden des Benutzers, falls er aktuell eingeloggt ist
        if (userSessionService.isLoggedIn() &&
                userSessionService.getLoggedInUsername().orElse("").equals(user.username())) {
            userSessionService.logout();
        }
    }

    @Override
    public void updateUser(User user) {
        if (userRepository.findUserByUsername(user.username()).isPresent()) {
            userRepository.saveUser(user); // Update durch erneutes Speichern
        } else {
            throw new IllegalArgumentException("User existiert nicht!");
        }
    }
}
