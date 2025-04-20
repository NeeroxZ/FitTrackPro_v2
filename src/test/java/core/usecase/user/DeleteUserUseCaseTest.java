package core.usecase.user;

import core.domain.user.User;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserUseCaseTest {

    @Test
    void shouldDeleteUserAndLogoutIfLoggedIn() {
        TestObjectBuilder b = new TestObjectBuilder().withDefaultUser();
        User user = b.getTestUser();
        DeleteUserUseCase useCase = new DeleteUserUseCase(b.userRepo, b.sessionMock);

        useCase.execute(user);

        assertFalse(b.userRepo.findUserByUsername(user.username()).isPresent());
        assertFalse(b.sessionMock.isLoggedIn());
    }

    @Test
    void shouldDeleteUserButDoNothingIfNotLoggedIn() {
        TestObjectBuilder b = new TestObjectBuilder().withDefaultUser();
        User user = b.getTestUser();
        b.sessionMock.logout(); // nicht eingeloggt

        DeleteUserUseCase useCase = new DeleteUserUseCase(b.userRepo, b.sessionMock);
        useCase.execute(user);

        assertFalse(b.userRepo.findUserByUsername(user.username()).isPresent());
    }

    @Test
    void shouldNotThrowIfUserDoesNotExist() {
        TestObjectBuilder b = new TestObjectBuilder();
        User ghost = b.makeUser("ghost");

        DeleteUserUseCase useCase = new DeleteUserUseCase(b.userRepo, b.sessionMock);

        assertDoesNotThrow(() -> useCase.execute(ghost)); // erwartet stilles Scheitern
    }
}
