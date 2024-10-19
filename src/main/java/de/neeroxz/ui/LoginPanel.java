package de.neeroxz.ui;

import de.neeroxz.user.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

/**
 * Class: LoginPanel
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class LoginPanel extends AbstractConsolePanel{

    private PasswordHasher passwordHasher;

    public LoginPanel(PasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
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
    }

    // TODO: Datenabank verbindungshalter
    private void login() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Benutzername: ");
            String username = scanner.nextLine();
            System.out.print("Passwort: ");
            Password password = Password.hash(scanner.nextLine(), passwordHasher);
            String url = "jdbc:h2:./fittracker"; // Die Datei "fittracker.mv.db" wird im aktuellen Verzeichnis gespeichert
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(url, "sa", "");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            UserRepository userRepository = new H2UserDB(connection);
            System.out.println(username + "  " + password.getHashedPassword());
            Optional<User> userOptional = userRepository.findUserByUsernameAndPassword(username, password.getHashedPassword());

            // Überprüfen, ob ein Benutzer mit den Anmeldeinformationen existiert
            if (userOptional.isPresent()) {
                User user = userOptional.get();  // Den Benutzer aus dem Optional extrahieren
                //LoggedInUser.getInstance(username);  // Benutzer im Singleton speichern
                System.out.println("Login erfolgreich!");
                break;  // Login erfolgreich, Schleife verlassen
            } else {
                System.out.println("Benutzername oder Passwort ist falsch. Bitte versuche es erneut.");
            }
            System.out.println("1: Erneut Versuchen");
            System.out.println("2: Beenden");
            System.out.print("Wähle eine Option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // Zeilenumbruch entfernen
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
