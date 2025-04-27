package adapters.cli.panels;

import adapters.cli.IInputReader;
import adapters.cli.validation.UserInputValidator;
import core.domain.user.Birthday;
import core.domain.user.User;
import core.ports.session.IUserSessionService;
import core.usecase.user.AuthenticationUserUseCase;
import core.usecase.user.RegisterUserUseCase;
import core.usecase.user.UpdateUserUseCase;
import core.usecase.user.UserUseCaseFactory;

import javax.swing.plaf.IconUIResource;
import java.util.Optional;

/**
 * CLI-Panel für Login und Registrierung.
 * Nutzt die UserUseCaseFactory für Authentifizierung und Registrierung.
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class LoginPanel extends AbstractConsolePanel {

    private final AuthenticationUserUseCase authenticationUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final IInputReader inputReader;
    private final UserInputValidator validator;
    private final UpdateUserUseCase updateUserUseCase;
    private final IUserSessionService userSessionService;

    public LoginPanel(UserUseCaseFactory userUseCaseFactory, IInputReader inputReader) {
        this.authenticationUserUseCase = userUseCaseFactory.authenticationUserUseCase();
        this.registerUserUseCase = userUseCaseFactory.registerUserUseCase();
        this.updateUserUseCase = userUseCaseFactory.updateUserUseCase();
        this.inputReader = inputReader;
        this.validator = new UserInputValidator(inputReader);
        this.userSessionService = userUseCaseFactory.userSessionService();
        removeMainMenu();
        addMenuAction("Login", this::login);
        addMenuAction("Registrieren", this::register);
        addMenuAction("Beenden", this::exitApp);
    }

    private void exitApp() {
        System.out.println("Programm wird beendet...");
        System.exit(0);
    }

    private void register() {
        String username = inputReader.readLine("Benutzername: ");
        String password = inputReader.readLine("Passwort: ");
        double gewicht = validator.readValidDouble("Gewicht (kg): ", 20, 300);
        double grosse = validator.readValidDouble("Größe (m): ", 0.5, 2.5);
        Birthday geburtstag = validator.readValidBirthday("Geburtsdatum (yyyy-mm-dd): ");

        boolean success = registerUserUseCase.execute(username, password, gewicht, grosse, geburtstag);
        if (success) {
            System.out.println("Registrierung erfolgreich! Du kannst dich jetzt einloggen.");
        } else {
            System.out.println("Fehler: Benutzername existiert bereits!");
        }
    }

    private void login() {
        while (true) {
            String username = inputReader.readLine("Benutzername: ");
            String password = inputReader.readLine("Passwort: ");

            Optional<User> user = authenticationUserUseCase.authenticate(username, password);

            if (user.isPresent()) {
                System.out.println("Login erfolgreich!");
                break;
            } else {
                System.out.println("Benutzername oder Passwort ist falsch. Bitte versuche es erneut.");
            }

            System.out.println("1: Erneut versuchen");
            System.out.println("2: Beenden");
            int option = inputReader.readInt("Wähle eine Option: ");

            if (option == 2) {
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
