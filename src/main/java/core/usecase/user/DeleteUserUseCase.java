package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.session.IUserSessionService;

public class DeleteUserUseCase {

    private final IUserRepository userRepository;
    private final IUserSessionService userSessionService;

    public DeleteUserUseCase(IUserRepository userRepository, IUserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.userSessionService = userSessionService;
    }

    public void execute(User user) {
        userRepository.deleteUser(user);

        // Falls der Benutzer eingeloggt war, ausloggen
        if (userSessionService.isLoggedIn() &&
                userSessionService.getLoggedInUsername().orElse("").equals(user.username())) {
            userSessionService.logout();
        }
    }
}
