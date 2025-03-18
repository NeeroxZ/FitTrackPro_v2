package de.neeroxz.adapters.cli.panels;

import de.neeroxz.core.domain.user.User;
import de.neeroxz.core.ports.services.IUserService;
import de.neeroxz.core.ports.session.IUserSessionService;
import de.neeroxz.adapters.cli.InputReader;

import java.util.Optional;

/**
 * Class: UserPanel
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class UserPanel extends AbstractConsolePanel
{

    private final IUserService userService;
    private final IUserSessionService userSessionService;
    private final InputReader inputReader; // CLI-Abstraktion für Nutzereingaben

    public UserPanel(IUserService userService, IUserSessionService userSessionService, InputReader inputReader)
    {
        this.userService = userService;
        this.userSessionService = userSessionService;
        this.inputReader = inputReader;

        super.addMenuAction("Gewicht ändern", this::changeWeight);
        super.addMenuAction("Benutzernamen ändern", this::changeUserName);
        super.addMenuAction("Account löschen", this::deleteAccount);
    }

    private void deleteAccount()
    {
        System.out.println("Möchten Sie Ihren Account wirklich löschen?");
        System.out.println("1. Ja");
        System.out.println("2. Nein");

        int choice = inputReader.readInt("Deine Wahl: "); // CLI-Abstraktion für Eingabe

        switch (choice)
        {
            case 1 ->
            {
                Optional<String> usernameOpt = userSessionService.getLoggedInUsername();
                if (usernameOpt.isEmpty())
                {
                    System.out.println("Kein Benutzer eingeloggt!");
                    return;
                }
                userService.findByUsername(usernameOpt.get()).ifPresentOrElse(
                        user ->
                        {
                            userService.deleteUser(user);
                            userSessionService.logout(); // Nutzer nach Löschung abmelden
                            System.out.println("Ihr Account wurde gelöscht. Sie wurden abgemeldet.");
                        },
                        () -> System.out.println("Benutzer nicht gefunden!")
                                                                             );
            }
            case 2 -> super.handleInput();
            default -> System.out.println("Ungültige Auswahl!");
        }
    }

    private void changeUserName()
    {
        Optional<String> usernameOpt = userSessionService.getLoggedInUsername();
        if (usernameOpt.isEmpty())
        {
            System.out.println("Kein Benutzer eingeloggt!");
            return;
        }

        String newUsername = inputReader.readLine("Neuer Benutzername: ");
        userService.findByUsername(usernameOpt.get()).ifPresent(user ->
                                                                {
                                                                    user = new User(newUsername, user.password(), user.gewicht(), user.grosse(), user.geburtstag());
                                                                    userService.updateUser(user);
                                                                    System.out.println("Benutzername wurde erfolgreich geändert.");
                                                                });
    }

    private void changeWeight()
    {
        Optional<String> usernameOpt = userSessionService.getLoggedInUsername();
        if (usernameOpt.isEmpty())
        {
            System.out.println("Kein Benutzer eingeloggt!");
            return;
        }

        double newWeight = inputReader.readValidDouble("Neues Gewicht (kg): ", 30.0, 200.0);
        userService.findByUsername(usernameOpt.get())
                   .ifPresent(user ->
                              {
                                  user = new User(user.username(), user.password(), newWeight, user.grosse(), user.geburtstag());
                                  userService.updateUser(user);
                                  System.out.println("Gewicht wurde erfolgreich geändert.");
                              });
    }

    @Override
    public void showPanel()
    {
        super.handleInput();
    }
}
