package adapters.security;

import core.domain.user.Password;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SHA256PasswordHasherTest {

    private final SHA256PasswordHasher hasher = new SHA256PasswordHasher();

    @Test
    void shouldHashSamePasswordToSameHash() {
        String raw = "geheim";
        Password hash1 = hasher.hash(raw);
        Password hash2 = hasher.hash(raw);

        assertEquals(hash1.getHashedPassword(), hash2.getHashedPassword());
    }

    @Test
    void shouldHashDifferentPasswordsToDifferentHashes() {
        Password hash1 = hasher.hash("passwort123");
        Password hash2 = hasher.hash("andersesPasswort");

        assertNotEquals(hash1.getHashedPassword(), hash2.getHashedPassword());
    }

    @Test
    void hashShouldBe64CharsLong() {
        Password hash = hasher.hash("123456");
        assertEquals(64, hash.getHashedPassword().length());
    }

    @Test
    void hashShouldBeHexEncoded() {
        Password hash = hasher.hash("abc123");

        assertTrue(hash.getHashedPassword().matches("[0-9a-f]+"));
    }
}
