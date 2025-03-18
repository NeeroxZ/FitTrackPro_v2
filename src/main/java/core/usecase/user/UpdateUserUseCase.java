package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.session.IUserSessionService;

public class UpdateUserUseCase {

    private final IUserRepository userRepository;
    private final IUserSessionService userSessionService;

    public UpdateUserUseCase(IUserRepository userRepository, IUserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
    }

    public void execute(User user) {
        if (userRepository.findUserByUsername(user.username()).isPresent()) {
            userRepository.saveUser(user); // Überschreiben mit neuer Instanz

            // Falls der Benutzer aktuell eingeloggt ist, aktualisieren wir ihn in der Session
            if (userSessionService.isLoggedIn() &&
                    userSessionService.getLoggedInUsername().orElse("").equals(user.username())) {
                userSessionService.setLoggedInUser(user);
            }

        } else {
            throw new IllegalArgumentException("User existiert nicht!");
        }
    }
}
