package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.services.IPasswordHasher;
import core.ports.session.IUserSessionService;

import java.util.Optional;

public class AuthenticationUserUseCase {
    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;
    private final IUserSessionService userSessionService; // ✅ Session wird benötigt

    public AuthenticationUserUseCase(IUserRepository userRepository, IPasswordHasher passwordHasher, IUserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.userSessionService = userSessionService;
    }

    public Optional<User> authenticate(String username, String password) {
        Optional<User> user = userRepository.findUserByUsername(username)
                                            .filter(u -> u.password().matches(password, passwordHasher));

        user.ifPresent(userSessionService::setLoggedInUser);

        return user;
    }
}
