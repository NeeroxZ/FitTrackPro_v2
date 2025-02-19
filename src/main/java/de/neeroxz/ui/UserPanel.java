package de.neeroxz.ui;

/**
 * Class: UserPanel
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class UserPanel extends AbstractConsolePanel{

    public UserPanel()
    {
        super.addMenuAction(
                "Gewicht ändern",
                this::changeWeight
        );
        super.addMenuAction(
                "Ändern Nutername",
                this::changeUserName
        );
        super.addMenuAction(
                "Account löschen",
                this::deleteAccount
        );
    }

    private void deleteAccount()
    {

        System.out.println("Möchten Sie ihren Account Wirklich löschen");
    }

    private void changeUserName()
    {

        System.out.println("Willst du deinen Namen ändern");
    }

    private void changeWeight()
    {
        System.out.println("Wie lautet dein neues Gewicht");
    }

    @Override
    public void showPanel()
    {
        super.handleInput();
    }
}
