package de.neeroxz.user;

/*
 * Class: LoggedInUser
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */
import java.util.Optional;

public class LoggedInUser {
    private static LoggedInUser instance;
    private final User user;

    private LoggedInUser(User user)
    {
        this.user = user;
    }

    public static void login(User user)
    {
        instance = new LoggedInUser(user);
    }

    public static void logout()
    {
        instance = null;
    }

    public static Optional<User> getCurrentUser()
    {
        return Optional
                .ofNullable(instance)
                .map(loggedInUser -> loggedInUser.user);
    }

    public static boolean isLoggedIn()
    {
        return instance != null;
    }
}

