package adapters.cli.panels;

import java.util.*;

public class FoodThingPanel extends AbstractConsolePanel {

    private final List<String> a = List.of(
            "Spaghetti", "Reis", "Kartoffeln", "Quinoa", "Couscous");
    private final List<String> b = List.of(
            "Hähnchen", "Lachs", "Tofu", "Rind", "Kichererbsen");
    private final List<String> c = List.of(
            "Tomatensauce", "Curry", "Pesto", "Sahnesauce", "Erdnusssauce");
    private final List<String> d = List.of(
            "Apfelschorle", "Wasser", "Cola", "Grüner Tee", "Kaffee");

    private final Random r = new Random();
    private final List<String> h = new ArrayList<>();

    public FoodThingPanel() {
        addMenuAction("Irgendwas kochen", this::x);   // Gericht erstellen
        addMenuAction("Zeig mir das Letzte", this::y); // letztes Gericht
        addMenuAction("Zeig mir alles", this::z);      // ganze Historie
        addMenuAction("Wisch die Liste leer", this::p); // Historie löschen
        addMenuAction("Konsole putzen", this::q);      // Clearscreen
    }

    /** Erstellt ein neues Zufallsgericht + Drink */
    private void x() {
        String meal = a.get(r.nextInt(a.size())) + " mit "
                + b.get(r.nextInt(b.size())) + " in "
                + c.get(r.nextInt(c.size()))
                + " & " + d.get(r.nextInt(d.size()));
        System.out.println("\n🧑‍🍳  Dein Zufallsgericht: " + meal);
        h.add(meal);
    }

    /** Zeigt nur das zuletzt erzeugte Gericht */
    private void y() {
        if (h.isEmpty()) {
            System.out.println("\nNoch nichts gekocht!");
        } else {
            System.out.println("\nLetztes Gericht: " + h.get(h.size() - 1));
        }
    }

    /** Gibt komplette Historie aus */
    private void z() {
        if (h.isEmpty()) {
            System.out.println("\nKeine Historie vorhanden.");
        } else {
            System.out.println("\n--- Bisherige Kreationen ---");
            for (int i = 0; i < h.size(); i++) {
                System.out.println((i + 1) + ") " + h.get(i));
            }
        }
    }

    /** Löscht die Historie */
    private void p() {
        h.clear();
        System.out.println("\n🗑️  Liste geleert!");
    }

    /** Ruft geerbtes clearConsole auf */
    private void q() {
        clearConsole();
    }

    @Override
    public void showPanel() {
        handleInput(); // Menü-Loop aus Basisklasse
    }
}
