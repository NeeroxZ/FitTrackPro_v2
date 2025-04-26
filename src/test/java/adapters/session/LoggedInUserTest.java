package adapters.session;

import core.domain.user.Birthday;
import core.domain.user.Password;
import core.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LoggedInUserTest {

    private LoggedInUser session;
    private User testUser;

    @BeforeEach
    void setUp() {
        session = new LoggedInUser();
        testUser = new User("testuser", Password.ofHashed("hashed"), 75.0, 1.80,
                            new Birthday(LocalDate.of(1990, 1, 1)));
    }

    @Test
    void getLoggedInUser_shouldReturnEmptyIfNotLoggedIn() {
        assertTrue(session.getLoggedInUser().isEmpty());
    }

    @Test
    void isLoggedIn_shouldReturnFalseIfNotLoggedIn() {
        assertFalse(session.isLoggedIn());
    }

    @Test
    void login_shouldSetUserAndBeLoggedIn() {
        session.login(testUser);
        assertTrue(session.isLoggedIn());
        assertEquals(testUser, session.getLoggedInUser().orElseThrow());
    }

    @Test
    void logout_shouldClearSession() {
        session.login(testUser);
        session.logout();
        assertFalse(session.isLoggedIn());
        assertTrue(session.getLoggedInUser().isEmpty());
    }

    @Test
    void getCurrentUsername_shouldReturnUsernameIfLoggedIn() {
        session.login(testUser);
        assertEquals("testuser", session.getCurrentUsername());
    }

    @Test
    void getCurrentUsername_shouldThrowIfNotLoggedIn() {
        assertThrows(UserNotLoggedInException.class, () -> session.getCurrentUsername());
    }

    @Test
    void setLoggedInUser_shouldReplaceCurrentUser() {
        User user1 = testUser;
        User user2 = new User("otheruser", Password.ofHashed("other"), 80.0, 1.90,
                              new Birthday(LocalDate.of(1995, 5, 5)));

        session.setLoggedInUser(user1);
        assertEquals("testuser", session.getCurrentUsername());

        session.setLoggedInUser(user2);
        assertEquals("otheruser", session.getCurrentUsername());
    }
}
