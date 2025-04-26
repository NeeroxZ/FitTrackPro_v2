package core.usecase.user;

import core.domain.user.Birthday;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;
import core.ports.services.DummyPasswordHasher;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUserUseCaseTest {

    @Test
    void shouldRegisterNewUserSuccessfully() {
        TestObjectBuilder b = new TestObjectBuilder();
        RegisterUserUseCase useCase = new RegisterUserUseCase(b.userRepo, new DummyPasswordHasher());

        boolean result = useCase.execute("newuser", "secret", 80.0, 1.80, new Birthday(LocalDate.of(1990, 1, 1)));

        assertTrue(result);
        assertTrue(b.userRepo.findUserByUsername("newuser").isPresent());
    }

    @Test
    void shouldNotRegisterDuplicateUser() {
        TestObjectBuilder b = new TestObjectBuilder().withDefaultUser();
        RegisterUserUseCase useCase = new RegisterUserUseCase(b.userRepo, new DummyPasswordHasher());

        boolean result = useCase.execute("testuser", "anypass", 70.0, 1.75, new Birthday(LocalDate.of(1990, 1, 1)));

        assertFalse(result);
    }
}
