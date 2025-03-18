package adapters.cli;

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

    @Override
    public double readValidDouble(String prompt, double min, double max)
    {
        double value;
        while (true)
        {
            System.out.print(prompt + " (" + min + " - " + max + "): ");
            try
            {
                value = scanner.nextDouble();
                scanner.nextLine(); // Verhindert Scanner-Probleme mit \n
                if (value >= min && value <= max)
                {
                    return value;
                }
                else
                {
                    System.out.println("Bitte einen Wert zwischen " + min + " und " + max + " eingeben.");
                }
            } catch (Exception e)
            {
                System.out.println("Ungültige Eingabe. Bitte eine Zahl eingeben.");
                scanner.nextLine(); // Eingabepuffer leeren
            }
        }
    }
}
