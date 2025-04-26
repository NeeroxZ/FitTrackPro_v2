package core.usecase.user;

import core.domain.user.User;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FindUserByUsernameUseCaseTest {

    @Test
    void shouldReturnUserIfFound() {
        TestObjectBuilder b = new TestObjectBuilder().withDefaultUser();
        FindUserByUsernameUseCase useCase = new FindUserByUsernameUseCase(b.userRepo);

        Optional<User> result = useCase.execute("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().username());
    }

    @Test
    void shouldReturnEmptyIfNotFound() {
        TestObjectBuilder b = new TestObjectBuilder();
        FindUserByUsernameUseCase useCase = new FindUserByUsernameUseCase(b.userRepo);

        Optional<User> result = useCase.execute("ghost");

        assertTrue(result.isEmpty());
    }
}
