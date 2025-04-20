package core.usecase.user;

import core.domain.user.User;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserUseCaseTest {

    @Test
    void shouldUpdateExistingUserAndSession() {
        TestObjectBuilder b = new TestObjectBuilder().withDefaultUser();
        User original = b.getTestUser();

        User updated = new User(
                original.username(),
                original.password(),
                85.0, // neues Gewicht
                original.grosse(),
                original.geburtstag()
        );

        UpdateUserUseCase useCase = new UpdateUserUseCase(b.userRepo, b.sessionMock);
        useCase.execute(updated);

        User loaded = b.userRepo.findUserByUsername(updated.username()).orElseThrow();
        assertEquals(85.0, loaded.gewicht());
        assertEquals(85.0, b.sessionMock.getLoggedInUser().orElseThrow().gewicht());
    }

    @Test
    void shouldThrowIfUserDoesNotExist() {
        TestObjectBuilder b = new TestObjectBuilder();
        User ghost = b.makeUser("ghost");

        UpdateUserUseCase useCase = new UpdateUserUseCase(b.userRepo, b.sessionMock);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(ghost));
    }
}
