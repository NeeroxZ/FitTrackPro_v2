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
    public Optional<User> findUserByUsernameAndPassword(String username, String hashedPassword);
}
