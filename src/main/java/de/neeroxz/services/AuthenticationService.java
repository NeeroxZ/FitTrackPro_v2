package de.neeroxz.services;

import de.neeroxz.user.*;

import java.util.Optional;

public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public AuthenticationService(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public Optional<User> authenticate(String username, String password) {
        String hashedPassword = Password.hash(password, passwordHasher).getHashedPassword();
        return userRepository.findUserByUsernameAndPassword(username, hashedPassword);
    }

    public boolean registerUser(String username, String password, double gewicht, double grosse, Birthday geburtstag) {
        if (userRepository.findUserByUsername(username).isPresent()) {
            return false; // Benutzer existiert bereits
        }

        String hashedPassword = Password.hash(password, passwordHasher).getHashedPassword();
        User newUser = new User(username, new Password(hashedPassword), gewicht, grosse, geburtstag);
        userRepository.saveUser(newUser);
        return true;
    }
}
