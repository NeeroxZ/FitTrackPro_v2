
-- Brust (CHEST)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Bench Press', 'BRUST', 'MEDIUM', 'Langhantel mit kontrollierter Bewegung nach unten und nach oben drücken.', 'CHEST'),
    ('Incline Bench Press', 'BRUST', 'HARD', 'Bank leicht schräg stellen, um den oberen Brustbereich stärker zu aktivieren.', 'CHEST,SHOULDERS'),
    ('Chest Fly', 'BRUST', 'EASY', 'Arme mit leichter Beugung ausbreiten und wieder zusammenführen.', 'CHEST'),
    ('Push-up', 'BRUST', 'EASY', 'Liegestütze für Brust, Schultern und Arme.', 'CHEST,ARMS,CORE'),
    ('Dips', 'BRUST', 'MEDIUM', 'Übung für Brust und Trizeps.', 'CHEST,ARMS');

-- Rücken (BACK)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Deadlift', 'RUECKEN', 'HARD', 'Kräftigung der hinteren Kette und des unteren Rückens.', 'BACK,LEGS,CORE'),
    ('Pull-up', 'RUECKEN', 'HARD', 'Klimmzüge für den Latissimus.', 'BACK,ARMS'),
    ('Lat Pulldown', 'RUECKEN', 'MEDIUM', 'Gezieltes Training des Latissimus mit Latzug.', 'BACK'),
    ('Bent-over Row', 'RUECKEN', 'MEDIUM', 'Langhantel-Rudern im vorgebeugten Stand.', 'BACK'),
    ('Face Pull', 'RUECKEN', 'EASY', 'Seilzug-Übung für hintere Schultern.', 'BACK,SHOULDERS');

-- Bizeps (ARMS)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Bicep Curl', 'BIZEPS', 'EASY', 'Isolationsübung für die Bizepsmuskulatur.', 'ARMS'),
    ('Hammer Curl', 'BIZEPS', 'EASY', 'Variation der Bizeps-Curls mit neutralem Griff.', 'ARMS'),
    ('Preacher Curl', 'BIZEPS', 'MEDIUM', 'Scott-Curl zur vollständigen Isolation.', 'ARMS');

-- Trizeps (ARMS)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Triceps Dips', 'TRIZEPS', 'MEDIUM', 'Übung für den Trizeps an parallelen Stangen.', 'ARMS'),
    ('Close-grip Bench Press', 'TRIZEPS', 'HARD', 'Enges Bankdrücken für Trizeps.', 'CHEST,ARMS'),
    ('Overhead Triceps Extension', 'TRIZEPS', 'EASY', 'Trizeps-Dehnung mit Kurzhantel über Kopf.', 'ARMS');

-- Schultern (SHOULDERS)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Overhead Press', 'VORDERE_SCHULTER', 'HARD', 'Langhantel oder Kurzhantel über den Kopf drücken.', 'SHOULDERS'),
    ('Front Raise', 'VORDERE_SCHULTER', 'EASY', 'Isolationsübung für vordere Schulter.', 'SHOULDERS'),
    ('Lateral Raise', 'SEITLICHE_SCHULTER', 'EASY', 'Seitliches Anheben der Arme.', 'SHOULDERS'),
    ('Reverse Fly', 'HINTERE_SCHULTER', 'EASY', 'Rückwärtsfliegen für hintere Schultern.', 'SHOULDERS,BACK');

-- Beine (LEGS)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Squats', 'BEINE', 'HARD', 'Kniebeugen zur Stärkung der Beinmuskulatur.', 'LEGS,CORE'),
    ('Lunges', 'BEINE', 'MEDIUM', 'Ausfallschritte zur Stärkung der Beine.', 'LEGS,CORE'),
    ('Leg Press', 'BEINE', 'MEDIUM', 'Gezieltes Training der Oberschenkel.', 'LEGS'),
    ('Calf Raises', 'BEINE', 'EASY', 'Wadenheben zur Stärkung der Waden.', 'LEGS');

-- Cardio (CARDIO)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Jogging', 'CARDIO', 'MEDIUM', 'Ausdauertraining zur kardiovaskulären Fitness.', 'LEGS,CORE'),
    ('Cycling', 'CARDIO', 'MEDIUM', 'Radfahren für gelenkschonendes Training.', 'LEGS'),
    ('Swimming', 'CARDIO', 'HARD', 'Ganzkörpertraining mit minimaler Belastung.', 'CHEST,BACK,ARMS,LEGS,CORE'),
    ('Jump Rope', 'CARDIO', 'MEDIUM', 'Springseiltraining für Ausdauer.', 'LEGS,CORE');

-- Yoga (YOGA)
INSERT INTO exercises (name, category, difficulty, description, muscle_groups)
VALUES
    ('Mountain Pose', 'YOGA', 'EASY', 'Haltung zur Verbesserung der Balance.', 'CORE'),
    ('Downward Dog', 'YOGA', 'MEDIUM', 'Dehnt hintere Kette und stärkt Schultern.', 'BACK,SHOULDERS,LEGS'),
    ('Tree Pose', 'YOGA', 'EASY', 'Gleichgewichtsübung zur Stabilitätsverbesserung.', 'CORE,LEGS');
