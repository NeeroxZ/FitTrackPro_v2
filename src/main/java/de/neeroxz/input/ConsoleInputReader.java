package de.neeroxz.input;

import java.util.Scanner;

public class ConsoleInputReader implements InputReader
{
    private final Scanner scanner;

    public ConsoleInputReader(Scanner scanner)
    {
        this.scanner = scanner;
    }

    @Override
    public String readLine(String prompt)
    {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    @Override
    public int readInt(String prompt)
    {
        System.out.print(prompt);
        while (!scanner.hasNextInt())
        {
            System.out.println("❌ Bitte eine gültige Nummer eingeben.");
            scanner.next(); // ungültigen Token verwerfen
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // Zeilenumbruch konsumieren
        return value;
    }
}
