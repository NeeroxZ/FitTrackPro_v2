package de.neeroxz.user.service;

import de.neeroxz.user.model.Password;
import de.neeroxz.user.model.User;

import java.util.Optional;

/**
 * Interface: UserRepository
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public interface UserRepository
{
    Optional<User> findUserByUsernameAndPassword(String username, Password password);
    Optional<User> findUserByUsername(String username);
    void saveUser(User user);
    void deleteUser(User user);
}
