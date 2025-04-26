package core.ports.session;

import core.domain.exercise.Exercise;
import core.domain.user.User;

import java.sql.Connection;
import java.util.List;

/**
 * Interface: SessionHolder
 *
 * @author NeeroxZ
 * @date 21.10.2024
 */
public interface ISessionHolder
{
    User getLoggedInUser();
    void setLoggedInUser(User user);
    Connection getDatabaseConnection();
    List<Exercise> getExercises();
    void setExercises(List<Exercise> exercises);
    void closeSession();
}

