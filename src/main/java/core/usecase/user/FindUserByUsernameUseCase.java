package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.IUserRepository;

import java.util.Optional;

/**
 * Use Case: FindUserByUsernameUseCase
 *
 * Findet einen Benutzer anhand des Benutzernamens.
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class FindUserByUsernameUseCase {

    private final IUserRepository userRepository;

    public FindUserByUsernameUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(String username) {
        return userRepository.findUserByUsername(username);
    }
}
