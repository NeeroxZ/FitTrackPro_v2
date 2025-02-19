package de.neeroxz.ui;

import de.neeroxz.util.AppStrings;

/**
 * Class: LoadingAnimation
 *
 * @author NeeroxZ
 * @date 19.10.2024
 */

public class LoadingAnimation {

    public void progressBar(int total)
    {
        int barLength = 50; // Länge des Fortschrittsbalkens
        System.out.println(AppStrings.ASCIIART.getAppString()); // Ausgabe der ASCII-Art

        for (int i = 0; i <= total; i++)
        {
            // Berechnung des Anteils
            double progress = (double) i / total;
            // Berechnung der Anzahl der Zeichen im Balken
            int chars = (int) (barLength * progress);

            // Erstellen des Fortschrittsbalkens
            StringBuilder bar = new StringBuilder();
            for (int j = 0; j < barLength; j++)
            {
                if (j < chars) {
                    bar.append("#"); // Teil des Fortschritts
                } else {
                    bar.append("-"); // Unvollendeter Teil
                }
            }

            // Ausgabe des Fortschritts
            System.out.print("\r[" + bar + "] " + (int) (progress * 100) + "%");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print("\nErfolgreich Geladen!\n"); // Abschlussausgabe
    }
}
