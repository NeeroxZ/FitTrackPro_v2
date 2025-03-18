package de.neeroxz.adapters.security;


import de.neeroxz.core.domain.user.Password;
import de.neeroxz.core.ports.services.IPasswordHasher;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class: SHA256PasswordHasher
 *
 * @author NeeroxZ
 * @date 12.10.2024
 */
public class SHA256PasswordHasher implements IPasswordHasher
{

    @Override
    public Password hash(String password)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

            // Umwandlung in Hex-String (optional könnte man auch Base64 verwenden)
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash)
            {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            // Rückgabe als Password-Objekt
            return Password.ofHashed(hexString.toString());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
