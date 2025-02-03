package de.neeroxz.user;

import java.util.Optional;

/**
 * Interface: UserRepository
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public interface UserRepository
{
    Optional<User> findUserByUsernameAndPassword(String username, String hashedPassword);
    Optional<User> findUserByUsername(String username);
    void saveUser(User user);
}
