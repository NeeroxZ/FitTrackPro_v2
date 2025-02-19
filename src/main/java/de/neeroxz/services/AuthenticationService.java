package de.neeroxz.services;

import de.neeroxz.user.*;

import java.util.Optional;

public class AuthenticationService {
    private final UserService userService;               // Statt UserRepository
    private final PasswordHasher passwordHasher;

    public AuthenticationService(UserService userService, PasswordHasher passwordHasher)
    {
        this.userService = userService;
        this.passwordHasher = passwordHasher;
    }

    public Optional<User> authenticate(String username, String password)
    {
        Password password1 = Password.hash(password, passwordHasher);
        Optional<User> userOptional = userService
                .findByUsernameAndPassword(username, password1);

        userOptional.ifPresent(LoggedInUser::login); // ✅ Speichert den User nach Login

        return userOptional;
    }


    public boolean registerUser(String username,
                                String password,
                                double gewicht,
                                double grosse,
                                Birthday geburtstag)
    {
        if (userService.findByUsername(username).isPresent())
        {
            return false; // Benutzer existiert bereits
        }

        String hashedPassword = Password.hash(password, passwordHasher).getHashedPassword();
        User newUser = new User(
                username,
                new Password(hashedPassword),
                gewicht,
                grosse,
                geburtstag
        );
        userService.saveUser(newUser);
        return true;
    }
}
