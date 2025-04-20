package core.usecase.user;

import core.ports.services.DummyPasswordHasher;
import org.junit.jupiter.api.Test;
import util.TestObjectBuilder;

import static org.junit.jupiter.api.Assertions.*;

class UserUseCaseFactoryTest {

    @Test
    void shouldProvideAllUserUseCases() {
        TestObjectBuilder b = new TestObjectBuilder();

        UserUseCaseFactory factory = new UserUseCaseFactory(
                b.userRepo,
                new DummyPasswordHasher(),
                b.sessionMock
        );

        assertNotNull(factory.authenticationUserUseCase());
        assertNotNull(factory.registerUserUseCase());
        assertNotNull(factory.updateUserUseCase());
        assertNotNull(factory.deleteAccountUseCase());
        assertNotNull(factory.changeUserNameUseCase());
        assertNotNull(factory.changeWeightUseCase());
        assertNotNull(factory.findUserByUsernameUseCase());
    }
}
