package core.ports.services;

import core.domain.user.Password;

/**
 * interface: PasswordHasher
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */

public interface IPasswordHasher
{
    Password hash(String password);
}