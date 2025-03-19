package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.session.IUserSessionService;

public class ChangeWeightUseCase {
    private final IUserRepository userRepository;
    private final IUserSessionService userSessionService;

    public ChangeWeightUseCase(IUserRepository userRepository, IUserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
    }

    public void execute(double newWeight) {
        String currentUsername = userSessionService.getLoggedInUser().map(User::username).orElseThrow(() ->
                                                                                              new IllegalStateException("Kein Benutzer eingeloggt!"));

        User existingUser = userRepository.findUserByUsername(currentUsername)
                                          .orElseThrow(() -> new IllegalArgumentException("Benutzer nicht gefunden!"));

        User updatedUser = new User(existingUser.username(), existingUser.password(), newWeight,
                                    existingUser.grosse(), existingUser.geburtstag());

        userRepository.saveUser(updatedUser);
        userSessionService.setLoggedInUser(updatedUser);
    }
}
