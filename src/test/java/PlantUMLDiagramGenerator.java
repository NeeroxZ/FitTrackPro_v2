


import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import de.elnarion.util.plantuml.generator.classdiagram.PlantUMLClassDiagramGenerator;
import de.elnarion.util.plantuml.generator.classdiagram.config.PlantUMLClassDiagramConfigBuilder;
import org.apache.commons.io.FileUtils;


public class PlantUMLDiagramGenerator
{
    public static void main(String[] args) {
        try {
            // Definiere die zu scannenden Packages
            List<String> scanPackages = new ArrayList<>();
           // scanPackages.add("adapters.persistence.inmemory");
           // scanPackages.add("core.domain.exercise");
           // scanPackages.add("core.ports.repository");
           // scanPackages.add("core.usecase.exercise");
            //scanPackages.add("adapters.security");
            scanPackages.add("core.usecase.workout");
            //scanPackages.add("app");
            //scanPackages.add("adapters");





            // Definiere Klassen, die im Diagramm ausgeblendet werden sollen
            List<String> hideClasses = new ArrayList<>();

            // Erstelle den Config-Builder mit den Scan-Paketen und verstecke bestimmte Klassen
            PlantUMLClassDiagramConfigBuilder configBuilder = new PlantUMLClassDiagramConfigBuilder(scanPackages)
                    .withHideClasses(hideClasses);

            // Erzeuge den Diagramm-Generator anhand der erstellten Konfiguration
            PlantUMLClassDiagramGenerator generator = new PlantUMLClassDiagramGenerator(configBuilder.build());

            // Generiere den UML-Quelltext als String
            String diagramText = generator.generateDiagramText();

            // Überprüfe, ob etwas generiert wurde
            if (diagramText == null || diagramText.trim().isEmpty()) {
                System.err.println("Es wurde kein Diagramm-Text generiert!");
                return;
            }

            // Definiere das Output-Verzeichnis und den Dateinamen
            File outputFile = new File("target/diagrams/generatedDiagram.puml");
            outputFile.getParentFile().mkdirs();

            // Schreibe den generierten Text in die Datei (Apache Commons IO wird hier verwendet)
            FileUtils.writeStringToFile(outputFile, diagramText, StandardCharsets.UTF_8);

            System.out.println("Diagram generated and saved to: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

