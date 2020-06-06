package customException;

public class CouldNotCreateRecordException extends RuntimeException{

    private static final long serialVersionUID = 577502565564657565L;

    public CouldNotCreateRecordException(String message)
    {
        super(message);
    }

}
