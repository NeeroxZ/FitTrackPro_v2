package de.neeroxz.ui;

/**
 * Class: MenuAction
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */
public class MenuAction {
    private final String name;
    private final Runnable action;

    public MenuAction(String name, Runnable action) {
        this.name = name;
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void execute() {
        action.run();
    }
}
