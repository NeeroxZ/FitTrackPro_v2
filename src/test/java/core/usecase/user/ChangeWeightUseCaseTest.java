package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.InMemoryUserRepositoryMock;
import core.ports.session.InMemoryUserSessionServiceMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.*;

class ChangeWeightUseCaseTest {

    private InMemoryUserRepositoryMock userRepo;
    private InMemoryUserSessionServiceMock session;
    private ChangeWeightUseCase useCase;
    private TestObjectBuilder b;

    @BeforeEach
    void setUp() {
        b = new TestObjectBuilder().withDefaultUser();
        userRepo = b.userRepo;
        session = b.sessionMock;
        useCase = new ChangeWeightUseCase(userRepo, session);

        // User ist schon in builder angelegt und eingeloggt
    }

    @Test
    void shouldUpdateWeightSuccessfully() {
        useCase.execute(77.5);

        User updated = userRepo.get(b.getTestUser().username());
        assertEquals(77.5, updated.gewicht());

        User sessionUser = session.getLoggedInUser().orElseThrow();
        assertEquals(77.5, sessionUser.gewicht());
    }

    @Test
    void shouldThrowIfNotLoggedIn() {
        session.logout();
        assertThrows(IllegalStateException.class, () -> useCase.execute(65));
    }

    @Test
    void shouldThrowIfUserNotFound() {
        session.setLoggedInUser(b.makeUser("ghost"));
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(65));
    }
}
