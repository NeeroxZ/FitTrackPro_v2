package de.neeroxz.ui;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class AbstractConsolePanelTest {
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Hilfsmethode, um simulierte Eingaben bereitzustellen.
     * @param data Simulierter Input, z. B. "1\n"
     */
    private void provideInput(String data) {
        testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    public void testHandleInputCallsExitPanel() throws InterruptedException {
        // Simuliere Eingabe: "1\n" -> Auswahl der Exit-Option
        provideInput("1\n");

        // Erstelle eine anonyme Subklasse von AbstractConsolePanel,
        // die exitPanel() überschreibt, um eine erkennbare Ausgabe zu erzeugen.
        AbstractConsolePanel panel = new AbstractConsolePanel() {
            @Override
            public void showPanel() throws InterruptedException {
                // Dummy-Implementierung, wird im Test nicht benötigt.
            }

            @Override
            protected void exitPanel() {
                System.out.println("Exit panel called");
            }
        };

        // Führe handleInput aus; es sollte die Exit-Option verarbeiten und exitPanel() aufrufen.
        panel.handleInput();

        // Erfasse die Konsolenausgabe und überprüfe, ob "Exit panel called" enthalten ist.
        String output = testOut.toString();
        assertTrue("Die Ausgabe sollte den Exit-Panel-Text enthalten.",
                   output.contains("Exit panel called"));
    }
}
