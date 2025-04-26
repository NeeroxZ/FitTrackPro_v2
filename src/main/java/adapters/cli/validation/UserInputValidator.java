package adapters.cli.validation;

/*
 * Class: UserInputValidator
 *
 * @author NeeroxZ
 * @date 03.02.2025
 */

import adapters.cli.IInputReader;
import core.domain.user.Birthday;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Validator-Klasse für Benutzereingaben in der CLI.
 */
public class UserInputValidator {
    private final IInputReader inputReader;

    public UserInputValidator(IInputReader inputReader) {
        this.inputReader = inputReader;
    }

    /**
     * Liest eine gültige Gleitkommazahl (double) innerhalb eines bestimmten Bereichs.
     * @param prompt Eingabeaufforderung
     * @param min Mindestwert
     * @param max Maximalwert
     * @return gültiger double-Wert
     */
    public double readValidDouble(String prompt, double min, double max) {
        while (true) {
            try {
                double value = inputReader.readValidDouble(prompt, min, max);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Ungültige Eingabe! Bitte eine gültige Zahl eingeben.");
            }
        }
    }

    /**
     * Liest ein gültiges Geburtsdatum im Format yyyy-MM-dd.
     * @param prompt Eingabeaufforderung
     * @return gültiges `Birthday`-Objekt
     */
    public Birthday readValidBirthday(String prompt) {
        while (true) {
            try {
                String input = inputReader.readLine(prompt);
                LocalDate date = LocalDate.parse(input);

                if (date.isAfter(LocalDate.now())) {
                    System.out.println("Das Geburtsdatum kann nicht in der Zukunft liegen.");
                    continue;
                }
                return new Birthday(date);
            } catch (DateTimeParseException e) {
                System.out.println("Ungültiges Datum! Bitte das Format yyyy-MM-dd verwenden.");
            }
        }
    }
}
