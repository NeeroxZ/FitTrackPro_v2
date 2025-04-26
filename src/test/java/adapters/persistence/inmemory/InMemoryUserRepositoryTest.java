package adapters.persistence.inmemory;

import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import adapters.security.SHA256PasswordHasher;
import core.ports.services.IPasswordHasher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;
    private final IPasswordHasher hasher = new SHA256PasswordHasher();

    private final User testUser = new User(
            "testuser",
            Password.hash("pass123", hasher),
            70.0,
            1.75,
            new Birthday(LocalDate.of(2000, 1, 1))
    );

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    void shouldFindUserByUsernameAndPassword_whenUserExists() {
        repository.saveUser(testUser);

        Optional<User> found = repository.findUserByUsernameAndPassword("testuser", Password.hash("pass123", hasher));
        assertTrue(found.isPresent());
        assertEquals("testuser", found.get().username());
    }

    @Test
    void shouldNotFindUserByUsernameAndPassword_whenPasswordIncorrect() {
        repository.saveUser(testUser);

        Optional<User> found = repository.findUserByUsernameAndPassword("testuser", Password.hash("wrong", hasher));
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldFindUserByUsername() {
        repository.saveUser(testUser);

        Optional<User> found = repository.findUserByUsername("testuser");
        assertTrue(found.isPresent());
    }

    @Test
    void shouldNotFindUserByUsername_whenNotExists() {
        Optional<User> found = repository.findUserByUsername("doesnotexist");
        assertTrue(found.isEmpty());
    }

    @Test
    void shouldSaveNewUser() {
        User newUser = new User("new", Password.hash("abc", hasher), 65, 1.65, new Birthday(LocalDate.of(1999, 2, 2)));
        repository.saveUser(newUser);

        assertTrue(repository.findUserByUsername("new").isPresent());
    }

    @Test
    void shouldOverwriteUser_whenSameUsername() {
        repository.saveUser(testUser);

        User updated = new User("testuser", Password.hash("newpass", hasher), 100, 2.0, testUser.geburtstag());
        repository.saveUser(updated);

        Optional<User> result = repository.findUserByUsernameAndPassword("testuser", Password.hash("newpass", hasher));
        assertTrue(result.isPresent());
        assertEquals(100, result.get().gewicht());
        assertEquals(2.0, result.get().grosse());
    }

    @Test
    void shouldDeleteUser() {
        repository.saveUser(testUser);
        assertTrue(repository.findUserByUsername("testuser").isPresent());

        repository.deleteUser(testUser);
        assertTrue(repository.findUserByUsername("testuser").isEmpty());
    }
}
