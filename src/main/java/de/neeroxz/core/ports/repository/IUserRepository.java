package de.neeroxz.core.ports.repository;

import de.neeroxz.core.domain.user.Password;
import de.neeroxz.core.domain.user.User;

import java.util.Optional;

/**
 * Interface: UserRepository
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public interface IUserRepository
{
    Optional<User> findUserByUsernameAndPassword(String username, Password password);
    Optional<User> findUserByUsername(String username);
    void saveUser(User user);
    void deleteUser(User user);
}
