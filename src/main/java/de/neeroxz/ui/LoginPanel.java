package de.neeroxz.ui;

import de.neeroxz.services.AuthenticationService;
import de.neeroxz.user.Birthday;
import de.neeroxz.user.UserInputValidator;

import java.time.LocalDate;
import java.util.Scanner;

public class LoginPanel extends AbstractConsolePanel {
    private final AuthenticationService authenticationService;
    private final Scanner scanner = new Scanner(System.in); //todo
    private final UserInputValidator validator;

    public LoginPanel(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.validator = new UserInputValidator(scanner);

        removeMainMenu();
        addMenuAction("Login", this::login);
        addMenuAction("Register", this::register);
        addMenuAction("Beenden", this::exitApp);
    }

    private void exitApp() {
        System.out.println("Programm wird beendet...");
        System.exit(0);
    }

    private void register() {
        System.out.print("Benutzername: ");
        String username = scanner.nextLine();
        System.out.print("Passwort: ");
        String password = scanner.nextLine();
        System.out.print("Gewicht (kg): ");
        double gewicht = validator.readValidDouble(
                "Gewicht (kg): ",
                20,
                300
        );
        double grosse = validator.readValidDouble(
                "Größe (m): ",
                0.5,
                2.5
        );
        Birthday geburtstag = validator.readValidBirthday(
                "Geburtsdatum (yyyy-mm-dd): "
        );

        boolean success = authenticationService.registerUser(
                username,
                password,
                gewicht,
                grosse,
                geburtstag
        );
        if (success) {
            System.out.println("Registrierung erfolgreich! Du kannst dich jetzt einloggen.");
        } else {
            System.out.println("Fehler: Benutzername existiert bereits!");
        }
    }


    private void login() {
        while (true) {
            System.out.print("Benutzername: ");
            String username = scanner.nextLine();
            System.out.print("Passwort: ");
            String password = scanner.nextLine();

            if (authenticationService.authenticate(username, password).isPresent())
            {
                System.out.println("Login erfolgreich!");
                break;
            } else {
                System.out.println("Benutzername oder Passwort ist falsch." +
                        " Bitte versuche es erneut.");
            }

            System.out.println("1: Erneut Versuchen");
            System.out.println("2: Beenden");
            System.out.print("Wähle eine Option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 2) {
                scanner.close();
                exitApp();
                return;
            }
        }
    }

    @Override
    public void showPanel() {
        super.handleInput();
    }
}
