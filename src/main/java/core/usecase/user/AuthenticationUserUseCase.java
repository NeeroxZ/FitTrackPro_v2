package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.services.IPasswordHasher;

import java.util.Optional;

public class AuthenticationUserUseCase {
    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;

    public AuthenticationUserUseCase(IUserRepository userRepository, IPasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findUserByUsername(username)
                             .filter(user -> user.password().matches(password, passwordHasher));
    }
}
