package adapters.session;

public class UserNotLoggedInException extends RuntimeException
{
    public UserNotLoggedInException(String message)
    {
        super(message);
    }
}
