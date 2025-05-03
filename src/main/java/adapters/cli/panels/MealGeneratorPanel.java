package adapters.cli.panels;

import java.util.*;

public class MealGeneratorPanel  extends AbstractConsolePanel {

    private final List<String> carbOptions    = List.of("Spaghetti", "Reis", "Kartoffeln", "Quinoa", "Couscous");
    private final List<String> proteinOptions = List.of("Hähnchen", "Lachs", "Tofu", "Rind", "Kichererbsen");
    private final List<String> sauceOptions   = List.of("Tomatensauce", "Curry", "Pesto", "Sahnesauce", "Erdnusssauce");
    private final List<String> drinkOptions   = List.of("Apfelschorle", "Wasser", "Cola", "Grüner Tee", "Kaffee");

    private final Random rand = new Random();
    private final List<String> mealHistory = new ArrayList<>();

    public void MealGeneratorPanel() {
        addMenuAction("Neues Gericht erzeugen", this::generateRandomMeal);
        addMenuAction("Letztes Gericht anzeigen", this::showLastMeal);
        addMenuAction("Komplette Historie anzeigen", this::showMealHistory);
        addMenuAction("Historie löschen", this::clearMealHistory);
        addMenuAction("Konsole leeren", this::clearScreen);
    }

    /** Erstellt ein neues Zufallsgericht + Drink */
    private void generateRandomMeal() {
        String meal = carbOptions.get(rand.nextInt(carbOptions.size())) + " mit "
                + proteinOptions.get(rand.nextInt(proteinOptions.size())) + " in "
                + sauceOptions.get(rand.nextInt(sauceOptions.size()))
                + " & " + drinkOptions.get(rand.nextInt(drinkOptions.size()));

        System.out.println("\n🧑‍🍳  Dein Zufallsgericht: " + meal);
        mealHistory.add(meal);
    }

    /** Zeigt nur das zuletzt erzeugte Gericht */
    private void showLastMeal() {
        if (mealHistory.isEmpty()) {
            System.out.println("\nNoch nichts gekocht!");
        } else {
            System.out.println("\nLetztes Gericht: " + mealHistory.get(mealHistory.size() - 1));
        }
    }

    /** Gibt komplette Historie aus */
    private void showMealHistory() {
        if (mealHistory.isEmpty()) {
            System.out.println("\nKeine Historie vorhanden.");
        } else {
            System.out.println("\n--- Bisherige Kreationen ---");
            for (int i = 0; i < mealHistory.size(); i++) {
                System.out.println((i + 1) + ") " + mealHistory.get(i));
            }
        }
    }

    /** Löscht die Historie */
    private void clearMealHistory() {
        mealHistory.clear();
        System.out.println("\n🗑️  Liste geleert!");
    }

    /** Ruft geerbtes clearConsole auf */
    private void clearScreen() {
        clearConsole();
    }

    @Override
    public void showPanel() {
        handleInput();
    }
}
