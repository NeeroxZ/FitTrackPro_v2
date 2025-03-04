package de.neeroxz.user.service;

import de.neeroxz.user.model.Password;
import de.neeroxz.user.model.User;

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
