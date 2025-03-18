package core.usecase.user;

import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.services.IPasswordHasher;

public class RegisterUserUseCase {
    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;

    public RegisterUserUseCase(IUserRepository userRepository, IPasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public boolean execute(String username, String password, double gewicht, double grosse, Birthday geburtstag) {
        if (userRepository.findUserByUsername(username).isPresent()) {
            return false; // Benutzername existiert bereits
        }

        Password hashedPassword = Password.hash(password, passwordHasher);
        User newUser = new User(username, hashedPassword, gewicht, grosse, geburtstag);
        userRepository.saveUser(newUser);
        return true;
    }
}
