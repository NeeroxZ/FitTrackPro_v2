# Programmentwurf - Protokoll

**Name:** [Name, Vorname]  
**Matrikelnummer:** [MNR]  
**Abgabedatum:** [DATUM]

---

## Kapitel 1: Einführung (4P)

### • Übersicht über die Applikation (1P)
**FitTrackerPro** ist eine Anwendung zur Erfassung und Analyse von Fitness- und Gesundheitsdaten. Sie ermöglicht es den Nutzern, Trainingspläne zu erstellen, Fortschritte zu verfolgen und Ernährungsdaten zu verwalten. Die Applikation unterstützt verschiedene Tracking-Methoden wie manuelle Eingabe, Integration mit Fitness-Trackern und KI-basierte Analysen zur Optimierung der persönlichen Fitnessziele.

**Funktionen:**
- <span style='color:green'>Erfassung von Workouts (Gewichtstraining, Cardio, HIIT, etc.)</span>
- <span style='color:green'>Tracking von Kalorien, Makronährstoffen und Wasserzufuhr</span>
- <span style='color:green'>Statistiken und Fortschrittsanalysen</span>
- <span style='color:green'>Integration mit Smartwatches und Fitness-Apps</span>
- <span style='color:green'>KI-gestützte Vorschläge zur Leistungssteigerung</span>
- <span style='color:red'>TODO: Soziale Funktionen (Freunde hinzufügen, Wettbewerbe erstellen)</span>
- <span style='color:red'>TODO: Gamification-Elemente (Achievements, Level-System)</span>
- <span style='color:red'>TODO: Erweiterte Analyse mit KI-basierten Vorhersagen</span>
- Erfassung von Workouts (Gewichtstraining, Cardio, HIIT, etc.)
- Tracking von Kalorien, Makronährstoffen und Wasserzufuhr
- Statistiken und Fortschrittsanalysen
- Integration mit Smartwatches und Fitness-Apps
- KI-gestützte Vorschläge zur Leistungssteigerung

**Problemstellung & Zweck:**  
Die Applikation hilft Nutzern, ihre Gesundheits- und Fitnessziele effizienter zu erreichen, indem sie Daten sammelt, auswertet und auf Basis von Mustern personalisierte Empfehlungen gibt. Dies erleichtert eine langfristige Motivation und die Anpassung von Trainings- und Ernährungsplänen an individuelle Bedürfnisse.

### • Starten der Applikation (1P)
Die Applikation kann mit dem normalen Java-Befehl in der Konsole ausgeführt werden:
```
java -jar FitTrackerPro.jar
```
Voraussetzungen:
- Installierte Java-Laufzeitumgebung (JRE) oder Java Development Kit (JDK)
- Das `FitTrackerPro.jar`-File muss sich im aktuellen Verzeichnis befinden.

### • Technischer Überblick (2P)
FitTrackerPro ist eine Java-basierte Anwendung, die mit **Maven** als Build-Tool verwaltet wird. Die wichtigsten Technologien sind:

- **Java 16**: Verwendet für die Kernentwicklung aufgrund der verbesserten Performance und Features.
- **Maven**: Verwaltung von Abhängigkeiten und Build-Prozess.
- **H2-Datenbank**: Eingesetzt für lokale Datenpersistenz und Tests.
- **JUnit**: Testframework für Unit-Tests.
- **PlantUML-Generator**: Automatische Erstellung von UML-Diagrammen zur Code-Dokumentation.
- **Commons-IO**: Bibliothek für erweiterte Dateioperationen.

Hier ist die `pom.xml`-Konfiguration der Abhängigkeiten:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>de.neeroxz</groupId>
  <artifactId>FitTrackPro</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>

  <dependencies>
    <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <version>2.2.220</version>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.2</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>de.elnarion.util</groupId>
      <artifactId>plantuml-generator-util</artifactId>
      <version>3.0.1</version>
    </dependency>
    <dependency>
      <groupId>commons-io</groupId>
      <artifactId>commons-io</artifactId>
      <version>2.11.0</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
</project>
```

---

## Kapitel 2: Softwarearchitektur (8P)

### • Gewählte Architektur (4P)
FitTrackerPro folgt einer **Layered Architecture (Schichtenarchitektur)** mit Ansätzen aus **Domain-Driven Design (DDD)**. Diese Architektur sorgt für eine klare Trennung der Verantwortlichkeiten, erleichtert die Wartbarkeit und ermöglicht eine bessere Testbarkeit.

#### **Architektur-Schichten**
1. **Presentation Layer (UI)**
    - Enthält alle Benutzeroberflächen- und Interaktionsklassen (`de.neeroxz.ui.*`).
    - Bietet die Konsole als Interface zur Eingabe und Ausgabe.

2. **Service Layer**
    - Kapselt die Geschäftslogik und regelt die Kommunikation mit den darunterliegenden Repositories.
    - Wichtige Services: `ExerciseService`, `WorkoutService`, `AuthenticationService`.

3. **Repository Layer**
    - Stellt Datenpersistenz-Funktionalität bereit.
    - Enthält Repository-Interfaces wie `ExerciseRepository`, `WorkoutRepository`, `UserRepository`.
    - Implementierungen wie `InMemoryExerciseRepository` simulieren eine persistente Speicherung.

4. **Persistence Layer**
    - `DatabaseConnectionFactory` verwaltet die Datenbankverbindung.
    - In-Memory-Datenbanken (`H2`) werden für Tests genutzt.

#### **Domain-Driven Design Elemente**
- **Entities:** `Exercise`, `Workout`, `User`.
- **Value Objects:** `Birthday`, `Password`.
- **Repositories:** `ExerciseRepository`, `WorkoutRepository`, `UserRepository` für persistente Datenhaltung.
- **Services:** Trennen Geschäftslogik von der UI und der Datenhaltung.

Diese Architektur sorgt für eine gute **Modularität, Testbarkeit und Skalierbarkeit**. Falls das System erweitert werden soll, können z. B. die In-Memory-Repositories durch eine echte Datenbank ersetzt werden.

### • Domain Code (1P)
[Erläuterung mit Code-Beispiel]

### • Analyse der Dependency Rule (3P)
- **Positiv-Beispiel** (inkl. UML)
- **Negativ-Beispiel** (inkl. UML)

---

## Kapitel 3: SOLID (8P)

### • Analyse SRP (3P)
- **Positiv-Beispiel** (inkl. UML)
- **Negativ-Beispiel** (inkl. UML)

### • Analyse OCP (3P)
- **Positiv-Beispiel** (inkl. UML)
- **Negativ-Beispiel** (inkl. UML)

### • Analyse [LSP/ISP/DIP] (2P)
- **Positiv-Beispiel** (inkl. UML)
- **Negativ-Beispiel** (inkl. UML)

---

## Kapitel 4: Weitere Prinzipien (8P)

### • Analyse GRASP: Geringe Kopplung (3P)
[UML und Begründung]

### • Analyse GRASP: [Polymorphismus/Pure Fabrication] (3P)
[UML und Begründung]

### • DRY (2P)
[Code-Beispiele (vorher/nachher), Begründung und Auswirkungen]

---

## Kapitel 5: Unit Tests (8P)

### • 10 Unit Tests (2P)
[Code mit Erklärung]

### • ATRIP: Automatic, Thorough und Professional (2P)
[Begründung und Testabdeckung]

### • Fakes und Mocks (4P)
[Analyse, Begründung und UML-Diagramme]

---

## Kapitel 6: Domain Driven Design (8P)

### • Ubiquitous Language (2P)
- **Bezeichnung | Bedeutung | Begründung**

### • Repositories (1,5P)
[UML und Begründung]

### • Aggregates (1,5P)
[UML und Begründung]

### • Entities (1,5P)
[UML und Begründung]

### • Value Objects (1,5P)
[UML und Begründung]

---

## Kapitel 7: Refactoring (8P)

### • Code Smells (2P)
- **[CODE SMELL 1]** (inkl. Code-Beispiel und Lösungsweg)
- **[CODE SMELL 2]** (inkl. Code-Beispiel und Lösungsweg)

### • 2 Refactorings (6P)
[Benennung, Anwendung, Begründung, UML vorher/nachher]

---

## Kapitel 8: Entwurfsmuster (8P)

### • Entwurfsmuster: [Name] (4P)
[Erklärung, UML-Diagramm]

### • Entwurfsmuster: [Name] (4P)
[Erklärung, UML-Diagramm]

