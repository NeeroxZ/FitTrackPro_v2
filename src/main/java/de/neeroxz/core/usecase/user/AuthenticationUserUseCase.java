package de.neeroxz.core.usecase.user;

import de.neeroxz.core.ports.services.IPasswordHasher;
import de.neeroxz.core.domain.user.Birthday;
import de.neeroxz.core.domain.user.Password;
import de.neeroxz.core.domain.user.User;
import de.neeroxz.core.ports.services.IUserService;
import de.neeroxz.adapters.session.LoggedInUser;

import java.util.Optional;

public class AuthenticationUserUseCase
{
    private final UserService userService;               // Statt UserRepository
    private final IPasswordHasher passwordHasher;

    public AuthenticationUserUseCase(UserService userService, IPasswordHasher passwordHasher)
    {
        this.userService = userService;
        this.passwordHasher = passwordHasher;
    }

    public Optional<User> authenticate(String username, String password)
    {
        Password password1 = Password.hash(password, passwordHasher);
        Optional<User> userOptional = userService
                .findByUsernameAndPassword(username, password1);

       // userOptional.ifPresent(LoggedInUser::login); // ✅ Speichert den User nach Login

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

    public IUserService getUserRepo()
    {
        return this.userService;
    }
}
