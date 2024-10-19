package de.neeroxz.user;

/**
 * interface: PasswordHasher
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */

public interface PasswordHasher {
    Password hash(String password);
}