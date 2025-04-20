package core.usecase.user;

import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import core.ports.repository.IUserRepository;
import core.ports.services.IPasswordHasher;
import core.ports.session.InMemoryUserSessionServiceMock;
import core.ports.session.IUserSessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationUserUseCaseTest {

    private IUserRepository userRepository;
    private IPasswordHasher passwordHasher;
    private IUserSessionService sessionService;
    private AuthenticationUserUseCase useCase;

    private final String username = "tester";
    private final String rawPassword = "secret123";
    private final String wrongPassword = "wrong";

    @BeforeEach
    void setUp() {
        // Einfacher Fake-Repo
        userRepository = new IUserRepository() {
            private final AtomicReference<User> user = new AtomicReference<>();

            {
                user.set(new User(
                        username,
                        Password.hash(rawPassword, getMockHasher()),
                        80.0,
                        1.85,
                        new Birthday(LocalDate.of(2000, 1, 1))
                ));
            }

            @Override
            public Optional<User> findUserByUsernameAndPassword(String username, Password password) {
                return Optional.empty(); // nicht verwendet
            }

            @Override
            public Optional<User> findUserByUsername(String username) {
                return Optional.ofNullable(user.get());
            }

            @Override
            public void saveUser(User user) {}

            @Override
            public void deleteUser(User user) {}
        };

        passwordHasher = getMockHasher();
        sessionService = new InMemoryUserSessionServiceMock();

        useCase = new AuthenticationUserUseCase(userRepository, passwordHasher, sessionService);
    }

    private IPasswordHasher getMockHasher() {
        return raw -> Password.ofHashed("hashed:" + raw);
    }

    @Test
    void authenticatesWithCorrectCredentials() {
        Optional<User> result = useCase.authenticate(username, rawPassword);
        assertTrue(result.isPresent());
        assertEquals(username, result.get().username());
    }

    @Test
    void failsWithWrongPassword() {
        Optional<User> result = useCase.authenticate(username, wrongPassword);
        assertTrue(result.isEmpty());
    }

    @Test
    void setsSessionOnSuccess() {
        useCase.authenticate(username, rawPassword);
        assertTrue(sessionService.isLoggedIn());
        assertEquals(username, sessionService.getLoggedInUser().get().username());
    }

    @Test
    void doesNotSetSessionOnFailure() {
        useCase.authenticate(username, wrongPassword);
        assertFalse(sessionService.isLoggedIn());
    }
}
