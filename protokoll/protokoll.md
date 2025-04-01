# Programmentwurf - Protokoll

**Name:** [Obreiter, Nick]  
**Matrikelnummer:** [8107127]  
**Abgabedatum:** [DATUM]

---

## Kapitel 1: Einführung (4P)

### • Übersicht über die Applikation (1P)

[Was macht die Applikation? Wie funktioniert sie? Welches Problem löst sie/welchen Zweck hat sie?]

**FitTrackerPro** ist eine prototyp zur Erfassung und generieung von
Workouts. Es wird dem Nutern ermöglicht Trainingspläne zu erstellen,
Fortschritte zu verfolgen und in Zukunft Ernährungsdaten zu verwalten.

**Funktionen:**

- <span style='color:green'>Generierung von Workouts (Gewichtstraining, Cardio, HIIT, etc.)</span>
- <span style='color:red'>TODO: Workout verlauf Speichern</span>
- <span style='color:red'>TODO: Ernährung</span>

### • Starten der Applikation (1P)

[Wie startet man die Applikation? Was für Voraussetzungen werden benötigt? Schritt-für-SchrittAnleitung]

Die Applikation kann mit dem normalen Java-Befehl in der Konsole
ausgeführt werden:

```bash
  java -jar FitTrackerPro.jar
```

Voraussetzungen:

- Installierte Java-Laufzeitumgebung (JRE) oder
  Java Development Kit (JDK)
- Das `FitTrackerPro.jar`-File muss sich im aktuellen Verzeichnis
  befinden.

---

### • Technischer Überblick (2P)

[Nennung und Erläuterung der Technologien (z.B. Java, MySQL, …), jeweils Begründung für den
Einsatz der Technologien]

FitTrackerPro ist eine Java-basierte Anwendung, die mit **Maven** als Build-Tool verwaltet wird. Die wichtigsten
Technologien sind:

- **Java 21**: Verwendet für die Kernentwicklung aufgrund der verbesserten Performance und Features.
- **Maven**: Verwaltung von Abhängigkeiten und Build-Prozess.
- **H2-Datenbank**: Eingesetzt für lokale Datenpersistenz.
- **JUnit**: Testframework für Unit-Tests.
- **PlantUML-Generator**: Automatische Erstellung von UML-Diagrammen zur Code-Dokumentation.
- **Commons-IO**: Bibliothek für erweiterte Dateioperationen.

---

## Kapitel 2: Softwarearchitektur (8P)

### • Gewählte Architektur (4P)

[In der Vorlesung wurden Softwarearchitekturen vorgestellt. Welche Architektur wurde davon
umgesetzt? Analyse und Begründung inkl. UML der wichtigsten Klassen, sowie Einordnung dieser
Klassen in die gewählte Architektur]

# Gewählte Architektur: Clean Architecture mit DDD

## Clean Architecture

- **Schichten:**
    1. **Domain (Entities):** Enthält zentrale Domänenobjekte wie `Exercise`, `User` und `Workout`.
    2. **Use Cases:** Realisieren Geschäftsprozesse, z. B. `CreateWorkoutUseCase` oder `AuthenticationUserUseCase`.
    3. **Interface Adapters:** Übersetzen Daten zwischen interner Logik und externen Schnittstellen.
    4. **Frameworks & Drivers:** Beinhaltet technische Details wie Datenbankzugriffe und UI-Frameworks.

- **Dependency Rule:**  
  Alle Abhängigkeiten verlaufen von außen nach innen, sodass die inneren Schichten (Domain und Use Cases) keine Kenntnis
  von technischen Implementierungen haben.

## Domain-Driven Design (DDD)

- **Fokus:**  
  Die Modellierung der fachlichen Zusammenhänge und Regeln steht im Mittelpunkt.

- **Elemente:**
    - **Entities & Value Objects:** Modelle wie `Exercise` oder `Birthday` definieren den Kern der Geschäftsdomäne.
    - **Repositories:** Abstraktion der persistierenden Datenspeicherung.
    - **Services "UseCases":** Geschäftslogik, die nicht direkt an eine einzelne Entität gebunden ist.

## Was ist Domain Code?

## Erklärung: Domain Code anhand des Exercise Records

Im Kontext von Domain-Driven Design (DDD) und Clean Architecture bildet der "Domain Code" den fachlichen Kern der
Anwendung. Er beinhaltet alle Komponenten, die die Geschäftsdomäne modellieren – also die zentralen Konzepte, Regeln und
Eigenschaften.

### Beispiel: `Exercise` Record

```java
package core.domain.exercise;

import java.util.List;

public record Exercise(
        int id,
        String name,
        ExerciseCategory category,
        Difficulty difficulty,
        String description,
        List<MuscleGroup> targetMuscles
) implements Comparable<Exercise>
{
    @Override
    public int compareTo(Exercise other)
    {
        return this.name.compareTo(other.name);
    }
}
```

### • Analyse der Dependency Rule (3P)

```
In der Vorlesung wurde im Rahmen der ‘Clean Architecture’ die s.g. Dependency Rule vorgestellt. Je 1
Klasse zeigen, die die Dependency Rule einhält und 1 Klasse, die die Dependency Rule verletzt;
jeweils UML (mind. die betreffende Klasse inkl. der Klassen, die von ihr abhängen bzw. von der sie
abhängt) und Analyse der Abhängigkeiten in beide Richtungen (d.h., von wem hängt die Klasse ab und
wer hängt von der Klasse ab) in Bezug auf die Dependency Rule
```

- **Positiv-Beispiel** (inkl. UML)


- **Abhängigkeiten von:**  
  `ServiceFactory` hängt von abstrakten Schnittstellen (Interfaces) ab, nicht von konkreten Klassen.
- **Abhängigkeiten zu:**  
  Hohe Schichten (z. B. Use Cases) greifen über die Factory auf diese Schnittstellen zu – ohne direkten Bezug zu den
  konkreten Implementierungen.
- **Fazit:**  
  Die Klasse folgt der Dependency Rule, da alle Abhängigkeiten von außen (über die Interfaces) nach innen fließen.

- **Negativ-Beispiel** (inkl. UML)

---

## Kapitel 3: SOLID (8P)

### • Analyse SRP (3P)

- **Positiv-Beispiel** (inkl. UML)
- **Negativ-Beispiel** (inkl. UML)
  ExerciseCreatorCLI

```java
  private int generateId()
{
    return (int) (Math.random() * 10000); // Dummy-Implementierung für eine zufällige ID
}

private ExerciseCategory getCategoryInput()
{
    while (true)
    {
        try
        {
            String input = scanner.nextLine().trim().toUpperCase();
            return ExerciseCategory.valueOf(input);
        } catch (IllegalArgumentException e)
        {
            System.out.print("Ungültige Kategorie. Bitte erneut eingeben (STRENGTH, CARDIO, MOBILITY): ");
        }
    }
}
````

### • Analyse OCP (3P)

Das Open/Closed-Prinzip besagt, dass Softwaremodule – insbesondere Klassen – für Erweiterungen offen, aber für
Modifikationen geschlossen sein sollten. Das bedeutet, wenn sich Anforderungen ändern, sollten wir das Verhalten eines
Moduls erweitern können, ohne den bestehenden Code anzupassen.

- **Positiv-Beispiel** (inkl. UML)

  Die Klasse `SHA256PasswordHasher` implementiert das Interface `IPasswordHasher`.  
  Wird ein neuer Hashing-Algorithmus benötigt, kann eine neue Klasse implementiert werden, ohne dass bestehender Code
  verändert werden muss.
  ![SHA256.png](images/SHA256.png)

- **Negativ-Beispiel** (inkl. UML)

  Die ursprüngliche Implementierung der Klasse `SmarterWorkoutGenerator` enthält in sich mehrere private Methoden (z. B.
  `getFullBodyWorkout`, `getUpperLowerWorkout`, `getPushPullLegWorkout`), die fest kodierte Logiken zur Generierung
  verschiedener Workout-Typen beinhalten.

  **Analyse:**  
  Möchte man einen neuen Workout-Typ hinzufügen, muss die Klasse direkt geändert werden (z. B. durch Hinzufügen neuer
  Methoden oder Anpassen der switch/case-Logik). Dadurch ist die Klasse nicht geschlossen für Änderungen, sondern muss
  modifiziert werden, was gegen das OCP verstößt.
  ![SmarterWorkoutGenerator.png](images/SmarterWorkoutGenerator.png)

### • Analyse [LSP/ISP/DIP] (2P)

- **Positiv-Beispiel** (inkl. UML)
- ## Beispiel für DIP: WorkoutUseCaseFactory

Die Klasse `WorkoutUseCaseFactory` ist ein gutes Beispiel für die Einhaltung des Dependency Inversion Principle (DIP).
Sie erhält alle benötigten Abhängigkeiten als abstrakte Schnittstellen, was bedeutet, dass sie nicht von konkreten
Implementierungen abhängt.

**Wichtige Punkte:**

- **Abstraktion statt Konkretheit:**  
  Die Factory erhält ihre Abhängigkeiten über die Interfaces `IWorkoutRepository`, `IUserSessionService` und
  `IWorkoutGenerator`. Dadurch sind die Use Cases von konkreten Implementierungen entkoppelt.

- **Flexibilität und Testbarkeit:**  
  Da die Abhängigkeiten als Schnittstellen übergeben werden, ist es einfach, alternative Implementierungen (z. B. für
  Tests) einzusetzen, ohne dass die Factory selbst angepasst werden muss.

- **Zentrale Erstellung der Use Cases:**  
  Die Factory kapselt die Logik zur Erstellung der Use Cases, sodass der höhere Anwendungslogik-Code nur mit diesen
  abstrakten Use Cases interagiert.
![WorkoutUseCaseFactory.png](images/WorkoutUseCaseFactory.png)
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

