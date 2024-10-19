package de.neeroxz.ui;

/**
 * Class: AppPanel
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */

public class AppPanel extends AbstractConsolePanel {

    public AppPanel() {

        // Menüaktionen dynamisch hinzufügen
        removeMainMenu();
        addMenuAction("User anlegen", this::createUser);
        addMenuAction("User anzeigen", this::showUsers);
        addMenuAction("Training auswählen", this::selectTraining);
        addMenuAction("Beenden", this::exitApp);

    }

    @Override
    public void showPanel() {
        new LoadingAnimation().progressBar(20);
        handleInput();
    }

    private void createUser() {
    }

    private void showUsers() {

    }

    private void selectTraining()
    {
        System.out.println("Trainingsauswahl (noch nicht implementiert).");
    }

    @Override
    protected boolean isExitOption(int choice) {
        // "Beenden" ist die letzte Option, nicht "1"
        return choice == getMenuActionCount();  // Beenden ist die letzte Option
    }

    private void exitApp() {
        System.out.println("Programm wird beendet...");
        System.exit(0);
    }
}


