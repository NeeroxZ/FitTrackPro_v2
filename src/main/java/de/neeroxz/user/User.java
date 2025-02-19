package de.neeroxz.user;

/**
 * Class: User
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public record User(
        String username,
        Password password,
        double gewicht,
        double grosse,
        Birthday geburtstag)
{}

