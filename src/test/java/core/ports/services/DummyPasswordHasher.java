package core.ports.services;

import core.domain.user.Password;

public class DummyPasswordHasher implements IPasswordHasher {
    @Override
    public Password hash(String input) {
        return new Password(input);
    }
}
