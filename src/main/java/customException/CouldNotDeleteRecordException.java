package customException;

public class CouldNotDeleteRecordException  extends RuntimeException{

    private static final long serialVersionUID = -577502565564658861L;

    public CouldNotDeleteRecordException(String message)
    {
        super(message);
    }
}

