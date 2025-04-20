package adapters.session;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserNotLoggedInExceptionTest {

    @Test
    @DisplayName("Sollte von RuntimeException erben")
    void shouldInheritFromRuntimeException() {
        UserNotLoggedInException exception = new UserNotLoggedInException("Test message");
        assertTrue(exception instanceof RuntimeException, "UserNotLoggedInException sollte von RuntimeException erben");
    }

    @Test
    @DisplayName("Sollte die korrekte Fehlermeldung haben")
    void shouldHaveCorrectMessage() {
        String testMessage = "Der Benutzer ist nicht angemeldet.";
        UserNotLoggedInException exception = new UserNotLoggedInException(testMessage);
        assertEquals(testMessage, exception.getMessage(), "Die Fehlermeldung der Exception sollte der übergebenen Meldung entsprechen");
    }

    @Test
    @DisplayName("Sollte eine Instanz mit einer anderen Nachricht korrekt erstellen")
    void shouldCreateInstanceWithAnotherMessage() {
        String anotherMessage = "Sitzung abgelaufen.";
        UserNotLoggedInException exception = new UserNotLoggedInException(anotherMessage);
        assertEquals(anotherMessage, exception.getMessage(), "Die Fehlermeldung sollte auch mit einer anderen Nachricht korrekt gesetzt werden");
    }
}