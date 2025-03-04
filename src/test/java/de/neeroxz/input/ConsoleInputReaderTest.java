package de.neeroxz.input;


import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleInputReaderTest {

    @Test
    public void testReadLine() {
        // Simuliere eine Zeileneingabe
        String simulatedInput = "Test input line\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        InputReader inputReader = new ConsoleInputReader(scanner);

        // Die Methode soll "Test input line" zurückgeben
        String result = inputReader.readLine("Enter a line: ");
        System.out.println(simulatedInput);
        assertEquals("Test input line", result);
    }

    @Test
    public void testReadIntValid() {
        // Simuliere eine gültige Zahleneingabe
        String simulatedInput = "123\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        InputReader inputReader = new ConsoleInputReader(scanner);

        int result = inputReader.readInt("Enter a number: ");
        System.out.println(simulatedInput);
        assertEquals(123, result);
    }

    @Test
    public void testReadIntWithInvalidThenValid() {
        // Simuliere eine ungültige Eingabe gefolgt von einer gültigen Zahl
        String simulatedInput = "abc\n456\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(inputStream);
        InputReader inputReader = new ConsoleInputReader(scanner);

        // Die Methode ignoriert den ungültigen Token und gibt schließlich 456 zurück
        int result = inputReader.readInt("Enter a number: ");
        System.out.println(simulatedInput);
        assertEquals(456, result);
    }
}
