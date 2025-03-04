package de.neeroxz.ui;

import de.neeroxz.user.service.IUserService;
import de.neeroxz.user.session.LoggedInUser;

import java.util.Scanner;

/**
 * Class: UserPanel
 *
 * @author NeeroxZ
 * @date 06.02.2025
 */
public class UserPanel extends AbstractConsolePanel{

    IUserService userService;
    public UserPanel(IUserService userService)
    {
        this.userService = userService;
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
        System.out.println("1. Ja");
        System.out.println("2. Nein");
        System.out.print("Deine Wahl: ");
        Scanner scanner = new Scanner(System.in);//todo Eventuell löschen ?
        int choice = scanner.nextInt();
        scanner.nextLine(); // Zeilenumbruch entfernen

        switch (choice)
        {
            case 1: userService.deleteUser( //todo gehört das hier rein ?
                    LoggedInUser
                            .getCurrentUser()
                            .orElseThrow(
                                    () -> new IllegalStateException("Kein User eingeloggt!")
                            )
                    );
            case 2: super.handleInput();
        }
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
