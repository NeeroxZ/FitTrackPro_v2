CREATE TABLE users (
                       username VARCHAR(255) PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       weight DOUBLE NOT NULL,
                       height DOUBLE NOT NULL,
                       birthday DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    difficulty VARCHAR(10) NOT NULL,
    description TEXT,
    muscle_groups VARCHAR(255) NOT NULL
    );

CREATE TABLE workouts (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          type VARCHAR(50) NOT NULL,
                          username VARCHAR(255) NOT NULL,
                          frequency INT NOT NULL,
                          split VARCHAR(50) NOT NULL,
                          FOREIGN KEY (username) REFERENCES users(username)
);
