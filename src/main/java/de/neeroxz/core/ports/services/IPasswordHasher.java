package de.neeroxz.core.ports.services;

import de.neeroxz.core.domain.user.Password;

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