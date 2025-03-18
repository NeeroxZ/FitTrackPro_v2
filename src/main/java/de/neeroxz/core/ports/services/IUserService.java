package de.neeroxz.core.ports.services;

import de.neeroxz.core.domain.user.Password;
import de.neeroxz.core.domain.user.User;

import java.util.Optional;

/**
 * Interface: IUserService
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public interface IUserService {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, Password hashedPassword);
    void saveUser(User user);
    void deleteUser(User user);
    void updateUser(User user);
}
