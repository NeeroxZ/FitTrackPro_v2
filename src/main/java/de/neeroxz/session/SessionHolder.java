package de.neeroxz.session;

import de.neeroxz.exercise.model.Exercise;
import de.neeroxz.user.model.User;

import java.sql.Connection;
import java.util.List;

/**
 * Interface: SessionHolder
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public interface SessionHolder {
    User getLoggedInUser();
    void setLoggedInUser(User user);
    Connection getDatabaseConnection();
    List<Exercise> getExercises();
    void setExercises(List<Exercise> exercises);
    void closeSession();
}

