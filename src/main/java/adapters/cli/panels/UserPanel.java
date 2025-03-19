package adapters.cli.panels;

import core.domain.user.User;
import core.ports.session.IUserSessionService;
import core.usecase.user.UserUseCaseFactory;
import adapters.cli.InputReader;

import java.util.Optional;

/**
 * Class: UserPanel
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class UserPanel extends AbstractConsolePanel
{

    private final UserUseCaseFactory userUseCaseFactory;
    private final IUserSessionService userSessionService;
    private final InputReader inputReader; // CLI-Abstraktion für Nutzereingaben

    public UserPanel(UserUseCaseFactory userUseCaseFactory, IUserSessionService userSessionService, InputReader inputReader)
    {
        this.userUseCaseFactory = userUseCaseFactory;
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

        int choice = inputReader.readInt("Deine Wahl: ");

        switch (choice)
        {
            case 1 ->
            {
                Optional<String> usernameOpt = userSessionService.getLoggedInUser().map(User::username);
                if (usernameOpt.isEmpty())
                {
                    System.out.println("Kein Benutzer eingeloggt!");
                    return;
                }

                userUseCaseFactory.findUserByUsernameUseCase().execute(usernameOpt.get()).ifPresentOrElse(
                        user ->
                        {
                            userUseCaseFactory.deleteAccountUseCase().execute(user);
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
        Optional<String> usernameOpt = userSessionService.getLoggedInUser().map(User::username);
        if (usernameOpt.isEmpty())
        {
            System.out.println("Kein Benutzer eingeloggt!");
            return;
        }

        String newUsername = inputReader.readLine("Neuer Benutzername: ");
        userUseCaseFactory.findUserByUsernameUseCase().execute(usernameOpt.get()).ifPresent(user ->
                                                                                            {
                                                                                                User updatedUser = new User(newUsername, user.password(), user.gewicht(), user.grosse(), user.geburtstag());
                                                                                                userUseCaseFactory.updateUserUseCase().execute(updatedUser);
                                                                                                userSessionService.setLoggedInUser(updatedUser); // Eingeloggten User aktualisieren
                                                                                                System.out.println("Benutzername wurde erfolgreich geändert.");
                                                                                            });
    }

    private void changeWeight()
    {
        Optional<User> usernameOpt = userSessionService.getLoggedInUser();
        if (usernameOpt.isEmpty())
        {
            System.out.println("Kein Benutzer eingeloggt!");
            return;
        }

        double newWeight = inputReader.readValidDouble("Neues Gewicht (kg): ", 30.0, 200.0);

        //todo macht zuviel
        userUseCaseFactory.findUserByUsernameUseCase()
                          .execute(usernameOpt
                                           .map(User::username)
                                           .orElseThrow(() -> new IllegalArgumentException("Username not found")))
                          .ifPresent(user -> {
                              User updatedUser = new User(user.username(), user.password(), newWeight, user.grosse(), user.geburtstag());
                              userUseCaseFactory.updateUserUseCase().execute(updatedUser);
                              userSessionService.setLoggedInUser(updatedUser); // Eingeloggten User aktualisieren
                              System.out.println("Gewicht wurde erfolgreich geändert.");
                          });
        userUseCaseFactory.findUserByUsernameUseCase().execute(usernameOpt.map(User::username).orElseThrow());
    }

    @Override
    public void showPanel()
    {
        super.handleInput();
    }
}
