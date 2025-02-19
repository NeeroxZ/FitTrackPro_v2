package de.neeroxz.user;

/*
 * Class: InMemoryUserRepository
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {
    private final List<User> users = new ArrayList<>();

    public InMemoryUserRepository()
    {
        // Optional: Standardbenutzer hinzufügen (z. B. für Tests)
        User testUser = new User(
                "test",
                Password.hash("test", new SHA256PasswordHasher()),
                75.0,
                1.80,
                new Birthday(LocalDate.of(1990, 5, 15)));

                users.add(testUser);
    }

    @Override
    public Optional<User> findUserByUsernameAndPassword(String username, Password password)
    {
        System.out.println(password.getHashedPassword());
        for (User user : users)
        {
            System.out.println(user.password());
        }
        return users.stream()
                .filter(user -> user.username()
                        .equals(username)
                        &&
                        user.password().getHashedPassword().equals(
                                password.getHashedPassword()
                        )
                )
                .findFirst();
    }

    @Override
    public Optional<User> findUserByUsername(String username)
    {
        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }

    @Override
    public void saveUser(User user) {
        if (findUserByUsername(user.username()).isPresent())
        {
            throw new RuntimeException("Benutzername bereits vergeben!");
        }
        users.add(user);
        System.out.println("✅ Benutzer gespeichert: " + user.username());
    }
}

