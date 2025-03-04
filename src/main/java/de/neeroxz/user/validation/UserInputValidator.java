package de.neeroxz.user.validation;

/*
 * Class: UserInputValidator
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import de.neeroxz.user.model.Birthday;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class UserInputValidator {
    private final Scanner scanner;

    public UserInputValidator(Scanner scanner)
    {
        this.scanner = scanner;
    }

    public double readValidDouble(String prompt, double min, double max)
    {
        while (true)
        {
            try
            {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine().trim());

                if (value < min || value > max) {
                    System.out.println("Bitte einen Wert zwischen "
                            + min
                            + " und "
                            + max
                            + " eingeben."
                    );
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe! Bitte eine gültige Zahl eingeben.");
            }
        }
    }

    public Birthday readValidBirthday(String prompt)
    {
        while (true)
        {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                LocalDate date = LocalDate.parse(input);

                if (date.isAfter(LocalDate.now()))
                {
                    System.out.println("Das Geburtsdatum kann nicht in der Zukunft liegen.");
                    continue;
                }
                return new Birthday(date);
            } catch (DateTimeParseException e) {
                System.out.println("Ungültiges Datum! Bitte das Format yyyy-mm-dd verwenden.");
            }
        }
    }
}
