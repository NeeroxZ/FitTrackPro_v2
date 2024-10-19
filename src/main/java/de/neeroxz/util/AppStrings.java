package de.neeroxz.util;

/**
 * Class: AppStrings
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */

public enum AppStrings {
    ASCIIART("  ______ _           _____                 _____                     \n" +
            " |  ____| |         |_   _|               |  __ \\                    \n" +
            " | |__  | |__   __ _| |   _ __   ___ _ __ | |  | | ___ _ __ ___  ___ \n" +
            " |  __| | '_ \\ / _` | | | | '_ \\ / _ \\ '_ \\| |  | |/ _ \\ '__/ __|/ _ \\\n" +
            " | |____| | | | (_| | | | | | | |  __/ | | | |__| |  __/ |  \\__ \\  __/\n" +
            " |______|_| |_|\\__,_| |_| |_| |_|\\___|_| |_|_____/ \\___|_|  |___/\\___|\n"
    ),
    LINESEPARATOR("------------------------------------------");

    private final String s;

    AppStrings(String s) {
        this.s = s;
    }

    // Getter-Methode
    public String getAppString() {
        return s;
    }
}
