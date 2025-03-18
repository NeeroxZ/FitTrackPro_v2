package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.session.IUserSessionService;

public class ChangeUserNameUseCase {
    private final IUserRepository userRepository;
    private final IUserSessionService userSessionService;

    public ChangeUserNameUseCase(IUserRepository userRepository, IUserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
    }

    public void execute(String newUsername) {
        String currentUsername = userSessionService.getLoggedInUsername().orElseThrow(() ->
                                                                                              new IllegalStateException("Kein Benutzer eingeloggt!"));

        User existingUser = userRepository.findUserByUsername(currentUsername)
                                          .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden!"));

        User updatedUser = new User(newUsername, existingUser.password(), existingUser.gewicht(),
                                    existingUser.grosse(), existingUser.geburtstag());

        userRepository.saveUser(updatedUser);
        userSessionService.setLoggedInUser(updatedUser);
    }

}
