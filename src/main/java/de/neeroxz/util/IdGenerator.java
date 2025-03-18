package de.neeroxz.util;

import java.util.UUID;

public class IdGenerator {

    private IdGenerator() {
        // Privater Konstruktor, um Instanzierung zu verhindern (Utility-Klasse)
    }

    public static int generateUniqueId() {
        return Math.abs(UUID.randomUUID().hashCode());
    }
}
