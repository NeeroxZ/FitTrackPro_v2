package core.usecase.user;

import core.ports.repository.IUserRepository;
import core.ports.services.IPasswordHasher;
import core.ports.session.IUserSessionService;

public class UserUseCaseFactory {
    private final IUserRepository userRepository;
    private final IPasswordHasher passwordHasher;
    private final IUserSessionService userSessionService;

    public UserUseCaseFactory(IUserRepository userRepository, IPasswordHasher passwordHasher, IUserSessionService userSessionService) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
        this.userSessionService = userSessionService;
    }

    public AuthenticationUserUseCase authenticationUserUseCase() {
        return new AuthenticationUserUseCase(userRepository, passwordHasher, userSessionService);
    }

    public RegisterUserUseCase registerUserUseCase() {
        return new RegisterUserUseCase(userRepository, passwordHasher);
    }

    public UpdateUserUseCase updateUserUseCase() {
        return new UpdateUserUseCase(userRepository, userSessionService);
    }

    public DeleteUserUseCase deleteAccountUseCase() {
        return new DeleteUserUseCase(userRepository, userSessionService);
    }

    public ChangeUserNameUseCase changeUserNameUseCase() {
        return new ChangeUserNameUseCase(userRepository, userSessionService);
    }

    public ChangeWeightUseCase changeWeightUseCase() {
        return new ChangeWeightUseCase(userRepository, userSessionService);
    }

    public FindUserByUsernameUseCase findUserByUsernameUseCase() {
        return new FindUserByUsernameUseCase(userRepository);
    }
    public IUserSessionService userSessionService(){
        return userSessionService;
    }
}
