package core.usecase.user;

import core.domain.user.User;
import core.ports.repository.InMemoryUserRepositoryMock;
import core.ports.session.InMemoryUserSessionServiceMock;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.*;

class ChangeUserNameUseCaseTest {

    @Test
    void shouldChangeUsernameSuccessfully() {
        TestObjectBuilder b = new TestObjectBuilder().withDefaultUser();
        ChangeUserNameUseCase useCase = new ChangeUserNameUseCase(b.userRepo, b.sessionMock);

        useCase.execute("newName");

        assertTrue(b.userRepo.findUserByUsername("newName").isPresent());
        assertEquals("newName", b.sessionMock.getLoggedInUser().orElseThrow().username());
    }

    @Test
    void shouldFailIfNoUserLoggedIn() {
        TestObjectBuilder b = new TestObjectBuilder(); // kein Login
        ChangeUserNameUseCase useCase = new ChangeUserNameUseCase(b.userRepo, b.sessionMock);

        assertThrows(IllegalStateException.class, () -> useCase.execute("newName"));
    }

    @Test
    void shouldFailIfUserNotFound() {
        TestObjectBuilder b = new TestObjectBuilder();
        User ghost = b.makeUser("ghost");
        b.sessionMock.setLoggedInUser(ghost); // nicht im Repo

        ChangeUserNameUseCase useCase = new ChangeUserNameUseCase(b.userRepo, b.sessionMock);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute("newName"));
    }
}
