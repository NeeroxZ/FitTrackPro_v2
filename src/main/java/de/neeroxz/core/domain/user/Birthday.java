package de.neeroxz.core.domain.user;

/*
 * Class: Birthday
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
import java.time.LocalDate;
import java.time.Period;

public class Birthday
{
    private final LocalDate birthDate;

    public Birthday(LocalDate birthDate)
    {
        if (birthDate.isAfter(LocalDate.now()))
        {
            throw new IllegalArgumentException("Geburtsdatum darf nicht in der Zukunft liegen!");
        }
        this.birthDate = birthDate;
    }

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    // Berechnung des Alters auf Basis des Geburtstags
    public int calculateAge()
    {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    // Validierung von spezifischen Anforderungen, falls nötig
    public boolean isAdult()
    {
        return calculateAge() >= 18;
    }
}
