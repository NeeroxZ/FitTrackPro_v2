package de.neeroxz.ui;

import de.neeroxz.user.Password;
import de.neeroxz.user.PasswordHasher;
import de.neeroxz.user.User;

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

    private void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Benutzername: ");
        String username = scanner.nextLine();
        System.out.print("Passwort: ");
        Password password = Password.hash(scanner.nextLine(),passwordHasher);

        // Hier wird der Benutzer aus der Datenbank geladen und verifiziert
        //User user = userRepository.findUserByUsername(username);
        scanner.close();
    }

    @Override
    public void showPanel() throws InterruptedException {

    }
}
