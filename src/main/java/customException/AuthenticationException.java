package customException;

public class AuthenticationException extends RuntimeException{

    private static final long serialVersionUID = -577502565564657565L;
    public AuthenticationException(String message)
    {
        super(message);
    }
}

